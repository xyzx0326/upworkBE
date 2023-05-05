package kodlamaio.northwind.queue;

import com.rabbitmq.client.Channel;
import kodlamaio.northwind.bean.SpeechTmpFile;
import kodlamaio.northwind.constant.MqConstant;
import kodlamaio.northwind.services.SpeechRecognitionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import utils.JsonUtils;

import javax.annotation.Resource;

/**
 * Receive all language speech recognition results
 * Optimize every language one queue
 * Maybe implement in your https://api.4aithings.com/api/stt/recognize service ??
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpeechAllLanguageHandler {

    @Resource
    private SpeechRecognitionService speechRecognitionService;

    @SneakyThrows
    @RabbitHandler
    @RabbitListener(queues = MqConstant.SPEECH_ALL_QUEUE)
    public void handler(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("speech all recognition receive message: {}", msg);
        SpeechTmpFile file = JsonUtils.toObject(msg, SpeechTmpFile.class);
        try {
            String recognize = speechRecognitionService.recognize(file);
            log.info("speech all recognition: {}", recognize);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("speech all recognition error", e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}

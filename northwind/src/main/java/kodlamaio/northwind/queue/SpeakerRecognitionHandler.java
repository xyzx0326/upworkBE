package kodlamaio.northwind.queue;

import com.rabbitmq.client.Channel;
import kodlamaio.northwind.bean.BaseTmpFile;
import kodlamaio.northwind.constant.MqConstant;
import kodlamaio.northwind.services.SpeakerRecognitionService;
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
 * Receive speaker recognition
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpeakerRecognitionHandler {

    @Resource
    private SpeakerRecognitionService speakerRecognitionService;

    @SneakyThrows
    @RabbitHandler
    @RabbitListener(queues = MqConstant.SPEAKER_RECOGNITION_QUEUE)
    public void handler(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("speaker recognition receive message: {}", msg);
        BaseTmpFile file = JsonUtils.toObject(msg, BaseTmpFile.class);
        try {
            String recognize = speakerRecognitionService.recognize(file);
            log.info("speaker recognition: {}", recognize);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("speaker recognition error", e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}

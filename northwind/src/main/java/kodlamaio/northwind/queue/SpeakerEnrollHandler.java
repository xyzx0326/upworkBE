package kodlamaio.northwind.queue;

import com.rabbitmq.client.Channel;
import kodlamaio.northwind.bean.SpeakerEnrollTmpFile;
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
public class SpeakerEnrollHandler {

    @Resource
    private SpeakerRecognitionService speakerRecognitionService;

    @SneakyThrows
    @RabbitHandler
    @RabbitListener(queues = MqConstant.SPEAKER_ENROLL_QUEUE)
    public void handler(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("speaker enroll receive message: {}", msg);
        SpeakerEnrollTmpFile file = JsonUtils.toObject(msg, SpeakerEnrollTmpFile.class);
        try {
            String recognize = speakerRecognitionService.enroll(file);
            log.info("speaker enroll: {}", recognize);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("speaker enroll error", e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}

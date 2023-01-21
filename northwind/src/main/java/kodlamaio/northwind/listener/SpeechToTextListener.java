package kodlamaio.northwind.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import kodlamaio.northwind.core.utilities.responses.RecognitionMessage;
import kodlamaio.northwind.services.SpeechRecognitionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpeechToTextListener {

//    private final ProcessRepository processRepository;

    private final RabbitTemplate rabbitTemplate;
    private final SpeechRecognitionService speechRecognitionService;

//    public SpeechToTextListener(ProcessRepository processRepository, RabbitTemplate rabbitTemplate, SpeechRecognitionService speechRecognitionService) {
public SpeechToTextListener(RabbitTemplate rabbitTemplate, SpeechRecognitionService speechRecognitionService) {
//        this.processRepository = processRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.speechRecognitionService = speechRecognitionService;
    }

    @RabbitListener(queues = "speech-recognition-queue")
    public void listen(Message message) throws IOException {
        byte[] messageBytes = message.getBody();
        ObjectMapper mapper = new ObjectMapper();
        RecognitionMessage recognitionMessage = mapper.readValue(messageBytes, RecognitionMessage.class);

        String userId = null;
//        Process recognitionProcess = Process.builder()
//                .userId(userId)
//                .parentProcess((long) 0)
//                .status(Status.WAITING)
//                .exchangeName("recognized-text-exchange")
//                .routingKey("recognized-text-routing-key")
//                .parameters(parameters.toString())
//                .build();
////                .filepath(recognitionMessage.getFilepath())
////                .language(recognitionMessage.getLanguage())
////                .build();

        String recognizedOutputJsonOpt = speechRecognitionService.recognize(recognitionMessage.getFilepath(), recognitionMessage.getLanguage());
//

//        processRepository.save(recognitionProcess);

        // Do something with the recognizedOutputJsonOpt, for example, send it back to a specific queue or save it to a database
        rabbitTemplate.convertAndSend("recognized-text-exchange", "recognized-text-routing-key", recognizedOutputJsonOpt);
    }

}

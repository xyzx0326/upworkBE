package kodlamaio.northwind.listener;

import org.springframework.stereotype.Component;

@Component
public class SpeechToTextListener {

//    private final RabbitTemplate rabbitTemplate;
//    private final SpeechRecognitionService speechRecognitionService;
//
//    public SpeechToTextListener(RabbitTemplate rabbitTemplate, SpeechRecognitionService speechRecognitionService) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.speechRecognitionService = speechRecognitionService;
//    }
//
//    @RabbitListener(queues = "speech-recognition-queue")
//    public void listen(Message message) throws IOException {
//        byte[] messageBytes = message.getBody();
//        ObjectMapper mapper = new ObjectMapper();
//        RecognitionMessage recognitionMessage = mapper.readValue(messageBytes, RecognitionMessage.class);
//
//        String recognizedOutputJsonOpt = speechRecognitionService.recognize(recognitionMessage.getFilepath(), recognitionMessage.getLanguage());
//        // Do something with the recognizedOutputJsonOpt, for example, send it back to a specific queue or save it to a database
//        rabbitTemplate.convertAndSend("recognized-text-exchange", "recognized-text-routing-key", recognizedOutputJsonOpt);
//    }
}
package kodlamaio.northwind.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import kodlamaio.northwind.core.utilities.responses.RecognitionMessage;
import kodlamaio.northwind.core.utilities.responses.RecognizedSpeechResponse;
import kodlamaio.northwind.services.SpeechRecognitionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@CrossOrigin(origins = {"https://localhost:8082","http://localhost:8082","http://localhost","https://localhost","http://localhost:4200",
        "https://www.4aithings.com","https://optimistic-water-71022.pktriot.net"})
@RequestMapping("/api/stt")
public class SpeechRecognitionController {

    private SpeechRecognitionService speechRecognitionService;

    List<String> files = new ArrayList<String>();
    private final Path rootLocation = Paths.get("/home/mesut");

    @Autowired
    public SpeechRecognitionController(SpeechRecognitionService speechRecognitionService) {
        super();
        this.speechRecognitionService = speechRecognitionService;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/savefile")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            try {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            } catch (Exception e) {
                throw new RuntimeException("FAIL!");
            }
            files.add(file.getOriginalFilename());

            message = "Successfully uploaded!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


//    @RequestMapping("/")
//    public String speechRecognitionPage() {
//        return "speech";
//    }

//    @ResponseBody
//    @RequestMapping(value="/init", method=POST)
    @PostMapping("/init")
    public void initModel(){
            try {
                speechRecognitionService.init();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    @PostMapping("/initAll")
    public void initAllModel(){
        try {
            speechRecognitionService.initAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
//    @ResponseBody
//    @RequestMapping(value="/recognize", method=POST)
    @PostMapping("/recognize")
    public ResponseEntity<String> speech_recognize(MultipartFile file, String language) {
        String message;
        File tempFile = null;
        try {
            String ofn = file.getOriginalFilename();

            tempFile = File.createTempFile(ofn, ".wav");
            file.transferTo(tempFile);

            Random rand = new Random();
            int int_random = rand.nextInt(1000);
            String p = "/media/mesut/Depo1/tested/" + ofn + "_" + String.valueOf(int_random);
            Path path = Paths.get(p);
            while (Files.exists(path)) {
                p = "/media/mesut/Depo1/tested/" + ofn + "_" + String.valueOf(int_random);
            }
            new File(p).mkdirs();
            File bfn = new File(p + "/" + ofn);
//            file.transferTo(tempFile);
            // Call speech recognition service
//            SpeechRecognitionMiddleware middleware = new SpeechRecognitionMiddleware();
//            String recognizedOutputJsonOpt = middleware.process(tempFile.getAbsolutePath(), language, speechRecognitionService);

            String recognizedOutputJsonOpt = speechRecognitionService.recognize(tempFile.getAbsolutePath(), language);
//
            tempFile.renameTo(bfn);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            // Create response object
            RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);
            response.setRecognizedText(recognizedOutputJsonOpt);
            message = "Successfully uploaded!";
            return ResponseEntity.status(HttpStatus.OK).body(recognizedOutputJsonOpt);
//            return ResponseEntity.ok(recognizedOutputJsonOpt);
        } catch (IOException e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        } finally {
            // Delete the temporary file
            tempFile.delete();
        }
    }
//}
    @PostMapping("/recognizequeue")
    public ResponseEntity<String> speech_recognize2(MultipartFile file, String language) {
//        String message;
        File tempFile = null;
        try {
            String ofn = file.getOriginalFilename();

            tempFile = File.createTempFile(ofn, ".wav");
            file.transferTo(tempFile);

            RecognitionMessage recognitionMessage = new RecognitionMessage();
            recognitionMessage.setFilepath(tempFile.getAbsolutePath());
            recognitionMessage.setLanguage(language);

            // Convert RecognitionMessage object to JSON and send to "speech-recognition-queue"
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(recognitionMessage);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            Message message = new Message(json.getBytes(), messageProperties);

            rabbitTemplate.send("speech-recognition-exchange", "speech-recognition-routing-key", message);

//            String recognizedOutputJsonOpt = (String) rabbitTemplate.receiveAndConvert("speech-recognition-queue");
//            // Create response object
//            RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);
//            response.setRecognizedText(recognizedOutputJsonOpt);
            return ResponseEntity.status(HttpStatus.OK).body("recognizedOutputJsonOpt");

        } catch (IOException e) {
//            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body( "Failed to upload!");
        } finally {
            // Delete the temporary file
            tempFile.delete();
        }
    }

    @PostMapping("/getResponse")
    public ResponseEntity<String> getResp() {
        byte[] messageBytes = (byte[]) rabbitTemplate.receiveAndConvert("recognized-text-queue");
        String recognizedOutputJsonOpt = new String(messageBytes);
        // Create response object
        RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);
        response.setRecognizedText(recognizedOutputJsonOpt);
        return ResponseEntity.status(HttpStatus.OK).body(recognizedOutputJsonOpt);
    }
}


//    @PostMapping("/recognize")
//    public RecognizedSpeechResponse speech_recognize(@RequestParam("file") MultipartFile file) {
//        String message;
//
//        String tmp ="/media/mesut/Depo1/works/NeMo/tools/asr_webapp/uploads/o.wav";
//        try {
//            file.transferTo(new File(tmp));
//            String recognizedOutputJsonOpt = speechRecognitionService.recognize(tmp);
////                String recognizedText = null;
//
//            //            if (recognizedText == null) {
//            //                return new RecognizedSpeechResponse(false, "Could not recognize speech");
//            //            }
//            RecognizedSpeechResponse r = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);
//            r.setRecognizedText(recognizedOutputJsonOpt);
//            return r;
////                message = "Successfully uploaded!";
////                return ResponseEntity.status(HttpStatus.OK).body(message);
//
//        } catch (IOException e) {
//            message = "Failed to upload!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
////                throw new RuntimeException(e);
//        }
//
//    }

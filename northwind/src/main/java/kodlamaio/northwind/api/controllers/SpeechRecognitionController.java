package kodlamaio.northwind.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import kodlamaio.northwind.core.utilities.responses.RecognitionMessage;
import kodlamaio.northwind.core.utilities.responses.RecognizedSpeechResponse;
//import kodlamaio.northwind.entities.concretes.Process;
import kodlamaio.northwind.services.SpeechRecognitionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        "https://www.4aithings.com","https://api.4aithings.com"})
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

    @PostMapping("/init")
    public void initModel(){
        try {
            speechRecognitionService.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/initPunct")
    public void initPunctModel(){
        try {
            speechRecognitionService.initPunct();
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

    @PostMapping("/recognize")
    public ResponseEntity<String> speech_recognize(MultipartFile file, String language) {
        String message;
        File tempFile = null;
        try {
            String ofn = file.getOriginalFilename();

//            tempFile = File.createTempFile(ofn, ".wav");
//            file.transferTo(tempFile);

            Random rand = new Random();
            int int_random = rand.nextInt(1000);
            String p = "/media/mesut/Depo1/tested/" + ofn + "_" + String.valueOf(int_random);
            Path path = Paths.get(p);
            while (Files.exists(path)) {
                p = "/media/mesut/Depo1/tested/" + ofn + "_" + String.valueOf(int_random);
            }
            new File(p).mkdirs();
            File bfn = new File(p + "/" + ofn);

            file.transferTo(bfn);
            String recognizedOutputJsonOpt = speechRecognitionService.recognize(bfn.getAbsolutePath(), language);

//            if (tempFile.exists()) {
//                tempFile.delete();
//            }
            // Create response object
            RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);
            response.setRecognizedText(recognizedOutputJsonOpt);

            return ResponseEntity.status(HttpStatus.OK).body(recognizedOutputJsonOpt);
        } catch (IOException e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/downloadVideo")
    public   ResponseEntity<byte[]> downloadVideo(String link) {
        String message;

        try {
            byte[] t =speechRecognitionService.downloadYoutubeVideo(link);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentType(MediaType.parseMediaType("audio/wav"));

            headers.setContentLength(t.length);
            return new ResponseEntity<byte[]>(t, headers, HttpStatus.OK);
//            return new ResponseEntity<>(t, headers, HttpStatus.OK);
        } catch (IOException e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }

    }

    @PostMapping("/recognizeQueue")
    public ResponseEntity<String> speech_recognize2(MultipartFile file, String language) {
        File tempFile = null;
//        Process process = new Process();
//        process.setProcessId();
        try {

            String originalFileName = file.getOriginalFilename();

            tempFile = File.createTempFile(originalFileName, ".wav", new File("/media/mesut/Depo1/tmp"));
            file.transferTo(tempFile);

            //Create RecognitionMessage object
            RecognitionMessage recognitionMessage = new RecognitionMessage();
            recognitionMessage.setFilepath(tempFile.getAbsolutePath());
            recognitionMessage.setLanguage(language);

            //Convert RecognitionMessage object to json string
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(recognitionMessage);

            //Create message properties
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

            //Create message
            Message message = new Message(json.getBytes(), messageProperties);

            //Send message to exchange
            rabbitTemplate.send("speech-recognition-exchange", "speech-recognition-routing-key", message);

            //Return response
            return ResponseEntity.status(HttpStatus.OK).body("Successfully uploaded and sent to speech recognition queue!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to upload!");
        }
    }

    @GetMapping("/getResponse")
    public ResponseEntity<String> getResp() {
        String recognizedOutputJsonOpt = (String) rabbitTemplate.receiveAndConvert("recognized-text-queue");
//        String recognizedOutputJsonOpt = new String(messageBytes);
        if(recognizedOutputJsonOpt == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        // Create response object
        RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);
        response.setRecognizedText(recognizedOutputJsonOpt);
        return ResponseEntity.status(HttpStatus.OK).body(recognizedOutputJsonOpt);
    }
}



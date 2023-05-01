package kodlamaio.northwind.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import kodlamaio.northwind.core.utilities.responses.RecognitionMessage;
import kodlamaio.northwind.core.utilities.responses.RecognizedSpeechResponse;
import kodlamaio.northwind.entities.concretes.Process;
import kodlamaio.northwind.services.SpeechRecognitionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin(origins = {"*"})
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


    @PostMapping("/recognize")
    public ResponseEntity<String> speech_recognize(MultipartFile file, String language) {
        String message;
        File tempFile = null;
        try {

            String recognizedOutputJsonOpt = speechRecognitionService.recognize(file, language);

            // Create response object
            RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);

            response.setRecognizedText(recognizedOutputJsonOpt);

            return ResponseEntity.status(HttpStatus.OK).body(recognizedOutputJsonOpt);
        } catch (IOException e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }



}



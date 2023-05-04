package kodlamaio.northwind.api.controllers;


import kodlamaio.northwind.core.utilities.responses.RecognizedSpeechResponse;
import kodlamaio.northwind.services.SpeechRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/stt")
public class SpeechRecognitionController {

    private SpeechRecognitionService speechRecognitionService;

    @Autowired
    public SpeechRecognitionController(SpeechRecognitionService speechRecognitionService) {
        super();
        this.speechRecognitionService = speechRecognitionService;
    }

    @PostMapping("/recognize")
    public ResponseEntity<String> speech_recognize(MultipartFile file, String language) {
        String message;
        try {

            String recognizedOutputJsonOpt = speechRecognitionService.receive(file, language);
            // Create response object
            RecognizedSpeechResponse response = new RecognizedSpeechResponse(true, recognizedOutputJsonOpt);

            response.setRecognizedText(recognizedOutputJsonOpt);

            return ResponseEntity.status(HttpStatus.OK).body(recognizedOutputJsonOpt);
        } catch (IOException e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /**
     * temporary read result
     *
     * @param id tmp file id
     * @return result
     */
    @GetMapping("/result/{id}")
    public String result(@PathVariable String id) {
        return speechRecognitionService.result(id);
    }


}



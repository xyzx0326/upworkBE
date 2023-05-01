package kodlamaio.northwind.api.controllers;

import io.swagger.annotations.ApiOperation;
import kodlamaio.northwind.core.utilities.responses.SpeakerResponse;
import kodlamaio.northwind.services.SpeakerRecognitionService;
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
import java.util.Random;
import java.util.UUID;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/sv")
public class SpeakerRecognitionController {
    private SpeakerRecognitionService speakerRecognitionService;

    @Autowired
    public SpeakerRecognitionController(SpeakerRecognitionService speakerRecognitionService) {
        super();
        this.speakerRecognitionService = speakerRecognitionService;
    }



    @PostMapping("/enroll")
    public ResponseEntity<String> enroll(String spk, @RequestParam("file") MultipartFile file) throws IOException {
        String tmp ="/media/mesut/Depo1/o.wav";


        String l = speakerRecognitionService.enroll(spk, file);
        boolean res = false;
        if(l=="success")
            res=true;
        else 
            res=false;
        SpeakerResponse r = new SpeakerResponse(res);
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }


    @PostMapping("/recognize")
    public ResponseEntity<String> recognize(@RequestParam("file") MultipartFile file){
        try {
            // validate the file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Invalid file provided");
            }



            // call the speaker recognition service
            String l = speakerRecognitionService.recognize(file);
//            SpeakerResponse r = new SpeakerResponse(true, l);
            return ResponseEntity.status(HttpStatus.OK).body(l);
        }
        catch (IllegalArgumentException ex) {
//            log.error("Invalid file provided: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (IOException ex) {
//            log.error("Error saving file: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}

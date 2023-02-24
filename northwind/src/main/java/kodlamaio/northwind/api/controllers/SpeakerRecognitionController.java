package kodlamaio.northwind.api.controllers;

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



@RestController
@CrossOrigin(origins = {"https://localhost:8082","http://localhost:8082","http://localhost","https://localhost","http://localhost:4200","http://4aithings.com:8082",
        "http://4aithings.com:4200","https://www.4aithings.com","http://www.4aithings.com","https://api.4aithings.com"})
@RequestMapping("/api/sv")
public class SpeakerRecognitionController {
    private SpeakerRecognitionService speakerRecognitionService;

    @Autowired
    public SpeakerRecognitionController(SpeakerRecognitionService speakerRecognitionService) {
        super();
        this.speakerRecognitionService = speakerRecognitionService;
    }

    @PostMapping("/init")
    public ResponseEntity<String> init() throws IOException {
        String message = "Failed to upload!";

        try {
            String l = speakerRecognitionService.init();
            message = "uploaded!";
            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (IOException e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/getSpeakers")
    public ResponseEntity<String> getSpeakers() throws IOException {
        String score = null;
        String l = speakerRecognitionService.getSpeakers();

        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

//    @ResponseBody
    @PostMapping("/enroll")
    public ResponseEntity<String> enroll(String spk, @RequestParam("file") MultipartFile file) throws IOException {
        String tmp ="/media/mesut/Depo1/o.wav";

        //TODO buradaki preprocess kodları düzenlenecek
        File fn = new File(tmp);
        if(fn.exists())
        {
            fn.delete();
        }
        String ofn = file.getOriginalFilename();
        Random rand = new Random();
        int int_random = rand.nextInt(1000);
        String p = "/media/mesut/Depo1/tested/"+ofn+"_"+String.valueOf(int_random);
        Path path = Paths.get(p);
        while (Files.exists(path)){
            p = "/media/mesut/Depo1/tested/"+ofn+"_"+String.valueOf(int_random);
        }

        new File(p).mkdirs();
        File bfn = new File(p+"/"+ofn);
        file.transferTo(bfn);
        file.transferTo(fn);

        String l = speakerRecognitionService.enroll(spk, tmp);
        boolean res = false;
        if(l=="success")
            res=true;
        else 
            res=false;
        SpeakerResponse r = new SpeakerResponse(res);
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(String spk, @RequestParam("file") MultipartFile file) throws IOException {
        String tmp ="/media/mesut/Depo1/o.wav";

        //TODO buradaki preprocess kodları düzenlenecek
        File fn = new File(tmp);
        if(fn.exists())
        {
            fn.delete();
        }
        String ofn = file.getOriginalFilename();
        Random rand = new Random();
        int int_random = rand.nextInt(1000);
        String p = "/media/mesut/Depo1/tested/"+ofn+"_"+String.valueOf(int_random);
        Path path = Paths.get(p);
        while (Files.exists(path)){
            p = "/media/mesut/Depo1/tested/"+ofn+"_"+String.valueOf(int_random);
        }

        new File(p).mkdirs();
        File bfn = new File(p+"/"+ofn);
        file.transferTo(bfn);
        file.transferTo(fn);
        String l = speakerRecognitionService.verify(spk, tmp);
        SpeakerResponse r = new SpeakerResponse(true, l);
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }
    @PostMapping("/recognize")
    public ResponseEntity<String> recognize(@RequestParam("file") MultipartFile file){
        try {
            // validate the file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Invalid file provided");
            }

            // create unique file name
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String tmp = "/media/mesut/Depo1/tmp/" + uniqueFileName;

            // create the folder
            String p = "/media/mesut/Depo1/tmp/" + uniqueFileName;
            Path path = Paths.get(p);
//            if (!Files.exists(path)){
//                new File(p).mkdirs();
//            }

            // save the file
//            File bfn = new File(p + "/" + uniqueFileName);
            file.transferTo(path);

            // call the speaker recognition service
            String l = speakerRecognitionService.recognize(tmp);
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

    @PostMapping("/diarize")
    public ResponseEntity<String> diarize(@RequestParam("file") MultipartFile file, int numSpeakers) throws IOException {
        try {
            // validate the file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Invalid file provided");
            }

            // create unique file name
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // create the folder
            String p = "/media/mesut/Depo1/tmp/" + uniqueFileName;
            Path path = Paths.get(p);

            // save the file
            file.transferTo(path);

            // call the speaker recognition service
            String l = speakerRecognitionService.diarize(p,numSpeakers);
            return ResponseEntity.status(HttpStatus.OK).body(l);
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean isValidFileType(MultipartFile file) {
        // check file type
        String fileType = file.getContentType();
        if (!fileType.equals("audio/wav") || !fileType.equals("audio/wave") || !fileType.equals("audio/x-wav")) {
            return false;
        }

        return true;
    }

}

package kodlamaio.northwind.api.controllers;

import kodlamaio.northwind.services.SpeakerRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/sv")
@RequiredArgsConstructor
public class SpeakerRecognitionController {

    private final SpeakerRecognitionService speakerRecognitionService;

    @PostMapping("/enroll")
    public String enroll(String spk, @RequestParam("file") MultipartFile file) {
        String tmp = "/media/mesut/Depo1/o.wav";
        return speakerRecognitionService.receiveEnroll(spk, file);
    }

    @GetMapping("/enroll/{id}")
    public String enrollResult(@PathVariable String id) {
        return speakerRecognitionService.enrollResult(id);
    }

    @PostMapping("/recognize")
    public String recognize(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Invalid file provided");
        }
        return speakerRecognitionService.receiveRecognize(file);
    }

    @GetMapping("/recognize/{id}")
    public String recognizeResult(@PathVariable String id) {
        return speakerRecognitionService.recognizeResult(id);
    }

}

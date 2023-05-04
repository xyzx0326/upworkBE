package kodlamaio.northwind.api.controllers;


import kodlamaio.northwind.services.SpeechRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/stt")
@RequiredArgsConstructor
public class SpeechRecognitionController {

    private final SpeechRecognitionService speechRecognitionService;

    @PostMapping("/recognize")
    public String speech_recognize(MultipartFile file, String language) {
        return speechRecognitionService.receive(file, language);
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



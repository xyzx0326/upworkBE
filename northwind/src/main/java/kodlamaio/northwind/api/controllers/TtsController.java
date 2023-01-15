package kodlamaio.northwind.api.controllers;

import kodlamaio.northwind.services.TtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {"https://localhost:8082","http://localhost:8082","http://localhost","https://localhost","http://localhost:4200","http://4aithings.com:8082",
        "http://4aithings.com:4200","https://www.4aithings.com","https://optimistic-water-71022.pktriot.net"})
@RequestMapping("/api/tts")
public class TtsController {
    private TtsService ttsService;

    @Autowired
    public TtsController(TtsService ttsService) {
        super();
        this.ttsService = ttsService;
    }

    @PostMapping(path = "/generateSpeech",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateSpeech(String sentence,
                                                 @RequestParam("lang") String lang) throws IOException {
        byte[] t = ttsService.generateSpeech(sentence, lang);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentType(MediaType.parseMediaType("audio/wav"));

        headers.setContentLength(t.length);
        return new ResponseEntity<>(t, headers, HttpStatus.OK);
    }


}

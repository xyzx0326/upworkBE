package kodlamaio.northwind.api.controllers;

import kodlamaio.northwind.services.NlpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {"https://localhost:8082","http://localhost:8082","http://localhost","https://localhost","http://localhost:4200",
        "https://www.4aithings.com","http://www.4aithings.com","https://api.4aithings.com"})
@RequestMapping("/api/nlp")
public class NlpController {
    private NlpService nlpService;

    @Autowired
    public NlpController(NlpService nlpService){
        super();
        this.nlpService = nlpService;
    }

    @PostMapping("/initAll")
    public void initAllModel() throws IOException {
        nlpService.initTG();
        nlpService.initParaphraser();
    }

    @PostMapping("/runParaphraser")
    public ResponseEntity<String> runParaphraser(String sentence) throws IOException {
        String outputJsonOpt = nlpService.runParaphraser(sentence);
        return ResponseEntity.status(HttpStatus.OK).body(outputJsonOpt);
    }

    @PostMapping("/runTG")
    public ResponseEntity<String> runTG(String sentence) throws IOException {
        String outputJsonOpt = nlpService.runTG(sentence);
        return ResponseEntity.status(HttpStatus.OK).body(outputJsonOpt);
    }

    @PostMapping("/runOpenAiEdit")
    public ResponseEntity<String> runOpenAiEdit(String sentence, String instruction) throws IOException {
        String outputJsonOpt = nlpService.runOpenAiEdit(sentence, instruction);
        return ResponseEntity.status(HttpStatus.OK).body(outputJsonOpt);
    }
}

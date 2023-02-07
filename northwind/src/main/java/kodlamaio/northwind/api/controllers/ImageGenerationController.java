package kodlamaio.northwind.api.controllers;

import kodlamaio.northwind.services.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
@CrossOrigin(origins = {"https://localhost:8082","http://localhost:8082","http://localhost","https://localhost","http://localhost:4200",
        "https://www.4aithings.com","https://api.4aithings.com"})
@RequestMapping("/api/IG")
public class ImageGenerationController {
    private ImageGenerationService imageGenerationService;

    @Autowired
    public ImageGenerationController(ImageGenerationService imageGenerationService) {
        super();
        this.imageGenerationService = imageGenerationService;
    }


    @PostMapping("/generateImage")
    public ResponseEntity<String> generateImage(String sentence) throws IOException {
            String t = imageGenerationService.generateImage(sentence);
            return new ResponseEntity<>(t, HttpStatus.OK);
    }

}

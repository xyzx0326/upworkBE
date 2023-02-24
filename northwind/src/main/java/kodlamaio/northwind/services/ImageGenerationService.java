package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ImageGenerationService {
//    private ImageGenerationService imageGenerationService;
//
//    public ImageGenerationService(ImageGenerationService imageGenerationService) {
//        super();
//        this.imageGenerationService = imageGenerationService;
//    }


    @PostMapping("/generateArt")
    public byte[] generateArt(String sentence) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(180, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("http://imagegen:8000/runIGArt/"+sentence)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        byte[] imageData = response.body().bytes();
//        String tmp = null;
//        tmp = (response.body().string());
        return imageData;
    }

    @PostMapping("/generateImage")
    public String generateImage(String sentence) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"prompt\": \""+sentence+"\",\n    \"n\": 1,\n    \"size\": \"256x256\"\n  }");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer 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")
                .build();
        Response response = client.newCall(request).execute();


        String tmp = null;
        tmp = (response.body().string());
        return tmp;
    }


    @PostMapping("/editImage")
    public String editImage(String sentence) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","@otter.png")
                .addFormDataPart("mask","@mask.png")
                .addFormDataPart("prompt",sentence)
                .addFormDataPart("n","2")
                .addFormDataPart("size","256x256")
                .build();
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/edits")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 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")
                .build();
        Response response = client.newCall(request).execute();

        String tmp = null;
        tmp = (response.body().string());
        return tmp;
    }

    @PostMapping("/variateImage")
    public String variateImage(String pathInputImage) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","@"+pathInputImage)
                .addFormDataPart("n","2")
                .addFormDataPart("size","256x256")
                .build();
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/variations")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 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")
                .build();
        Response response = client.newCall(request).execute();

        String tmp = null;
        tmp = (response.body().string());
        return tmp;
    }

}

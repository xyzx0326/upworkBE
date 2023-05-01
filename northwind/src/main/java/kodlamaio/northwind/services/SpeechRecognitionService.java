package kodlamaio.northwind.services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;

import static kodlamaio.northwind.core.utilities.TextNormalizer.normalizeToTurkishChars;

@Service
public class SpeechRecognitionService {









    public String recognize(MultipartFile file, String language) throws IOException {
        String modelName = null;


        //this part is yyou need to work. You can try english or turkish model
        if( language.equals("Turkish") || language.equals("English") || language.equals("Hindi") || language.equals("German") || language.equals("French") || language.equals("Russian") || language.equals("Spanish")){


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody fileBody = RequestBody.create(MediaType.parse("audio/x-wav"), file.getBytes());

            // Add the file to the MultipartBody.Builder instance
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "audio.wav", fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.4aithings.com/api/stt/recognize?language=" + language)
                    .method("POST", requestBody)
                    .addHeader("accept", "*/*")
                    .addHeader("Content-Type", "multipart/form-data")
                    .build();


            String tmp = null;
            try (Response response = client.newCall(request).execute()) {
                tmp = (response.body().string());
                if(language.equals("Turkish") ){
                    tmp=normalizeToTurkishChars(tmp);
                }
                response.body().close();
            }
            return tmp;
        }
        else{
            return "Language not supported";
        }
    }





}



//}

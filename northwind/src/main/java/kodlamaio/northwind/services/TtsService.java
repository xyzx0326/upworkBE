package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

@Service
public class TtsService {


    public byte[] generateSpeech(String sentence, String language) throws IOException {
        String modelName=language;

        if(language.equals("Turkish")){
            sentence=sentence.replace(" ","%20");
            sentence = sentence.toLowerCase(new Locale("tr", "TR"));
            modelName="Turkish01";
        }


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "ttsModel="+modelName+"&sentence="+sentence);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:1111/synthesize")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("/media/mesut/Depo1/tts.wav"));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] wavData = out.toByteArray();

//        return data

//        File audioFile = new File(response.body());
//        client.

        return wavData;
    }
}

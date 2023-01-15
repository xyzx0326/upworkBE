package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SpeechRecognitionService {

    public void init() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "model=49.nemo");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9080/initialize_model")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

    }
    public void initAll() throws IOException {


        List<String> allmodels = new ArrayList();
        allmodels.add("49.nemo");
        allmodels.add("stt_hi_conformer_ctc_medium.nemo");
        allmodels.add("stt_de_conformer_ctc_large.nemo");
        allmodels.add("stt_en_conformer_ctc_large.nemo");
        allmodels.add("stt_enes_conformer_ctc_large.nemo");
        allmodels.add("stt_es_conformer_ctc_large.nemo");
        allmodels.add("stt_fr_conformer_ctc_large.nemo");
        allmodels.add("stt_ru_conformer_ctc_large.nemo");

        for(String modelSTT : allmodels) {
            OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS).build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String respModel="model="+modelSTT;
            RequestBody body = RequestBody.create(mediaType, respModel);
            Request request = new Request.Builder()
                    .url("http://192.168.1.50:9080/initialize_model")
                    .method("POST", body)
                    .addHeader("accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            response.close();
        }
    }
    public String recognize(String wavPath, String language) throws IOException {
        String modelName = null;
        if(language.equals("Turkish")){
            modelName="49.nemo";
        } else if (language.equals("English")) {
            modelName="stt_en_conformer_ctc_xlarge.nemo";
        }
        else if (language.equals("Hindi")) {
            modelName="stt_hi_conformer_ctc_medium.nemo";        }
        else if (language.equals("German")) {
            modelName="stt_de_conformer_ctc_large.nemo";
        } else if (language.equals("French")) {
            modelName="stt_fr_conformer_ctc_large.nemo";
        } else if (language.equals("Russian")) {
            modelName="stt_ru_conformer_ctc_large.nemo";
        } else if (language.equals("Spanish")) {
            modelName="stt_es_conformer_ctc_large.nemo";
        } else if (language.equals("English-Spanish")) {
            modelName="stt_enes_conformer_ctc_large.nemo";
        }

        String cont="model="+modelName+"&input speech="+wavPath;
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, cont);
            Request request = new Request.Builder()
                    .url("http://192.168.1.50:9080/transcribeSeg")
                    .method("POST", body)
                    .addHeader("accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept-Encoding", "UTF-8")
                    .build();
        String tmp=null;
        try (Response response = client.newCall(request).execute()) {
            tmp = (response.body().string());
            tmp = tmp.replace("\\u0131", "ı");
            tmp = tmp.replace("\\u015f", "ş");
            tmp = tmp.replace("\\u00e7", "ç");
            tmp = tmp.replace("\\u011f", "ğ");
            tmp = tmp.replace("\\u00fc", "ü");
            tmp = tmp.replace("\\u00f6", "ö");
            response.body().close();
        }
        return  tmp;
    }


}

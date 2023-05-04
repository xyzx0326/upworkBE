package kodlamaio.northwind.services;

import kodlamaio.northwind.bean.TmpFile;
import kodlamaio.northwind.config.ApplicationConfig;
import kodlamaio.northwind.constant.MqConstant;
import kodlamaio.northwind.enums.Language;
import okhttp3.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.JsonUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static kodlamaio.northwind.core.utilities.TextNormalizer.*;

@Service
public class SpeechRecognitionService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ApplicationConfig applicationConfig;

    private static final Map<String, String> result = new ConcurrentHashMap<>();


    public String receive(MultipartFile file, String language) throws IOException {
        // A simple way to process the file, may not be the best way
        // create tmp file
        UUID uuid = UUID.randomUUID();
        File dir = new File(applicationConfig.getTmpDir());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File dest = new File(applicationConfig.getTmpDir() + "/" + uuid);
        Files.copy(file.getInputStream(), dest.toPath());
        // send message to queue
        // TODO: save file info to db
        TmpFile message = new TmpFile();
        message.setLanguage(language);
        message.setId(uuid.toString());
        message.setName(file.getOriginalFilename());
        rabbitTemplate.convertAndSend(MqConstant.SPEECH_EXCHANGE, language, JsonUtils.toJson(message));
        return uuid.toString();
    }

    public String recognize(TmpFile tmpFile) throws IOException {
        String language = tmpFile.getLanguage();

        File file = new File(applicationConfig.getTmpDir() + "/" + tmpFile.getId());
        if (!file.exists()) {
            return "File not found";
        }

        // this part is you need to work. You can try english or turkish model
        Language lang = Language.getLanguage(language);
        if (lang != null) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody fileBody = RequestBody.create(MediaType.parse("audio/x-wav"),
                                                      Files.readAllBytes(file.toPath()));

            // Add the file to the MultipartBody.Builder instance
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", tmpFile.getName(), fileBody)
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
                if (lang == Language.TURKISH) {
                    tmp = normalizeToTurkishChars(tmp);
                }
                response.body().close();
            }

            // TODO: maybe save result to db or other place ?
            result.put(tmpFile.getId(), tmp);
            return tmp;
        } else {
            result.put(tmpFile.getId(), "Language not supported");
            return "Language not supported";
        }
    }

    /**
     * temporary read result
     *
     * @param id tmp file id
     * @return result
     */
    public String result(String id) {
        if (result.containsKey(id)) {
            return result.remove(id);
        }
        return null;
    }

}

package kodlamaio.northwind.services;

import kodlamaio.northwind.bean.BaseTmpFile;
import kodlamaio.northwind.bean.SpeakerEnrollTmpFile;
import kodlamaio.northwind.cache.ResultCache;
import kodlamaio.northwind.config.ApplicationConfig;
import kodlamaio.northwind.constant.MqConstant;
import okhttp3.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.FileUtils;
import utils.HttpUtils;
import utils.JsonUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class SpeakerRecognitionService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ApplicationConfig applicationConfig;

    @Resource
    private ResultCache cache;

    public String getSpeakers() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://spk:9081/getEnrolledSpeakers")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String tmp = (response.body().string());
        response.close();

        tmp = tmp.replace("\\u0131", "ı");
        tmp = tmp.replace("\\u015f", "ş");
        tmp = tmp.replace("\\u00e7", "ç");
        tmp = tmp.replace("\\u011f", "ğ");
        tmp = tmp.replace("\\u00fc", "ü");
        tmp = tmp.replace("\\u00f6", "ö");

        return tmp;
    }

    public String enroll(SpeakerEnrollTmpFile tmpFile) throws IOException {
        File file = new File(applicationConfig.getTmpDir() + "/" + tmpFile.getId());
        if (!file.exists()) {
            return "File not found";
        }
        String url = "https://api.4aithings.com/api/sv/enroll?spk=" + tmpFile.getSpk();
        String ret = HttpUtils.postMultipart(url, tmpFile.getName(), Files.readAllBytes(file.toPath()));
        cache.set(tmpFile.getId(), ret);
        // delete file if needed
        //        file.delete();
        return ret;
    }

    public String recognize(BaseTmpFile tmpFile) throws IOException {
        File file = new File(applicationConfig.getTmpDir() + "/" + tmpFile.getId());
        if (!file.exists()) {
            return "File not found";
        }
        String url = "https://api.4aithings.com/api/sv/recognize";
        String ret = HttpUtils.postMultipart(url, tmpFile.getName(), Files.readAllBytes(file.toPath()));
        cache.set(tmpFile.getId(), ret);
        // delete file if needed
        //        file.delete();
        return ret;
    }

    public String receiveEnroll(String spk, MultipartFile file) {

        String id = FileUtils.saveFile(applicationConfig.getTmpDir(), file);
        // send message to queue
        // TODO: save file info to db
        SpeakerEnrollTmpFile message = new SpeakerEnrollTmpFile();
        message.setSpk(spk);
        message.setId(id);
        message.setName(file.getOriginalFilename());
        rabbitTemplate.convertAndSend(MqConstant.SPEAKER_ENROLL_EXCHANGE, MqConstant.SPEAKER_ENROLL_QUEUE,
                                      JsonUtils.toJson(message));
        return id;
    }

    public String receiveRecognize(MultipartFile file) {
        String id = FileUtils.saveFile(applicationConfig.getTmpDir(), file);
        // send message to queue
        // TODO: save file info to db
        BaseTmpFile message = new BaseTmpFile();
        message.setId(id);
        message.setName(file.getOriginalFilename());
        rabbitTemplate.convertAndSend(MqConstant.SPEAKER_RECOGNITION_EXCHANGE, MqConstant.SPEAKER_RECOGNITION_QUEUE,
                                      JsonUtils.toJson(message));
        return id;
    }

    /**
     * same as speech result
     */
    public String enrollResult(String id) {
        if (cache.contains(id)) {
            return cache.delete(id);
        }
        return null;
    }

    /**
     * same as speech result
     */
    public String recognizeResult(String id) {
        if (cache.contains(id)) {
            return cache.delete(id);
        }
        return null;
    }

}

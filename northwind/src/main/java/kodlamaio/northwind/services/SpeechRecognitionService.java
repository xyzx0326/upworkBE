package kodlamaio.northwind.services;

import kodlamaio.northwind.bean.SpeechTmpFile;
import kodlamaio.northwind.cache.ResultCache;
import kodlamaio.northwind.config.ApplicationConfig;
import kodlamaio.northwind.constant.MqConstant;
import kodlamaio.northwind.enums.Language;
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

import static kodlamaio.northwind.core.utilities.TextNormalizer.*;

@Service
public class SpeechRecognitionService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ApplicationConfig applicationConfig;

    @Resource
    private ResultCache cache;

    public String receive(MultipartFile file, String language) {
        // A simple way to process the file, may not be the best way
        // create tmp file

        Language lang = Language.getLanguage(language);

        if (lang == null) {
            throw new RuntimeException("Language not supported");
        }

        String id = FileUtils.saveFile(applicationConfig.getTmpDir(), file);
        // send message to queue
        // TODO: save file info to db
        SpeechTmpFile message = new SpeechTmpFile();
        message.setLanguage(lang);
        message.setId(id);
        message.setName(file.getOriginalFilename());
        rabbitTemplate.convertAndSend(MqConstant.SPEECH_EXCHANGE, language, JsonUtils.toJson(message));
        return id;
    }

    public String recognize(SpeechTmpFile tmpFile) throws IOException {
        Language language = tmpFile.getLanguage();

        File file = new File(applicationConfig.getTmpDir() + "/" + tmpFile.getId());
        if (!file.exists()) {
            return "File not found";
        }
        // this part is you need to work. You can try english or turkish model
        String url = "https://api.4aithings.com/api/stt/recognize?language=" + language.getValue();
        String resp = HttpUtils.postMultipart(url, tmpFile.getName(), Files.readAllBytes(file.toPath()));
        if (language == Language.TURKISH) {
            resp = normalizeToTurkishChars(resp);
        }
        // TODO: maybe save result to db or other place ?
        cache.set(tmpFile.getId(), resp);
        // delete file if needed
        //        file.delete();
        return resp;
    }

    /**
     * temporary read result
     *
     * @param id tmp file id
     * @return result
     */
    public String result(String id) {
        if (cache.contains(id)) {
            return cache.delete(id);
        }
        return null;
    }

}

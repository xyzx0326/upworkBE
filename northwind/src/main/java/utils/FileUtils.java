package utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class FileUtils {

    public static String saveFile(String path, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File dest = new File(path + "/" + uuid);
        try {
            Files.copy(file.getInputStream(), dest.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
        return uuid.toString();
    }

}

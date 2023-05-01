package kodlamaio.northwind.core.utilities.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class SpeechToTextRequest {
    MultipartFile mediaFile;

    @Schema(defaultValue = "Turkish")
    String language;

    @Schema(defaultValue = "1")
    String userId;

//    @Schema(defaultValue = "1")
//    private String processId;

}

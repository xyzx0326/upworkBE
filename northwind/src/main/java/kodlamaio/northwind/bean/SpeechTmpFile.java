package kodlamaio.northwind.bean;

import kodlamaio.northwind.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpeechTmpFile extends BaseTmpFile {

    private Language language;

}

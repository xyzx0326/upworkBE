package kodlamaio.northwind.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpeakerEnrollTmpFile extends BaseTmpFile {

    private String spk;

}

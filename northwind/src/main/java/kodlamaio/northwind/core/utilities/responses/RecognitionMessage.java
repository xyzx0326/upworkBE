package kodlamaio.northwind.core.utilities.responses;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RecognitionMessage implements Serializable {
    private String filepath;
    private String language;
//    private Process process;

}

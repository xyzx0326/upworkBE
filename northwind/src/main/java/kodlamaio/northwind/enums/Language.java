package kodlamaio.northwind.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {

    TURKISH("Turkish"),
    ENGLISH("English"),
    HINDI("Hindi"),
    GERMAN("German"),
    FRENCH("French"),
    RUSSIAN("Russian"),
    SPANISH("Spanish")
    ;

    private final String value;

    public static Language getLanguage(String value) {
        for (Language language : Language.values()) {
            if (language.getValue().equals(value)) {
                return language;
            }
        }
        return null;
    }

}

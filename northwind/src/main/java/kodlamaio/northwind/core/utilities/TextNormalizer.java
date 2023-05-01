package kodlamaio.northwind.core.utilities;

public class TextNormalizer {
    public static String normalizeToTurkishChars(String tmp) {
        tmp = tmp.replace("\\u0131", "ı");
        tmp = tmp.replace("\\u015f", "ş");
        tmp = tmp.replace("\\u00e7", "ç");
        tmp = tmp.replace("\\u011f", "ğ");
        tmp = tmp.replace("\\u00fc", "ü");
        tmp = tmp.replace("\\u00f6", "ö");
        tmp = tmp.replace("\\u0130", "İ");
        tmp = tmp.replace("\\u015e", "Ş");
        tmp = tmp.replace("\\u00c7", "Ç");
        tmp = tmp.replace("\\u011e", "Ğ");
        tmp = tmp.replace("\\u00dc", "Ü");
        tmp = tmp.replace("\\u00d6", "Ö");
        tmp = tmp.replace("\\n", "");
        tmp = tmp.replace("\\", "");
        tmp = tmp.replace("\"\"", "\"");
        return tmp;
    }
}


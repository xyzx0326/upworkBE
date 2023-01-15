package kodlamaio.northwind.core.utilities.responses;

public class RecognizedSpeechResponse {
    private boolean success;
    private String recognizedText;

    public RecognizedSpeechResponse(boolean success, String recognizedText) {
        this.success = success;
        this.recognizedText = recognizedText;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRecognizedText() {
        return recognizedText;
    }

    public void setRecognizedText(String recognizedText) {
        this.recognizedText = recognizedText;
    }
}

package kodlamaio.northwind.core.utilities.responses;

import java.util.List;

public class SpeakerResponse {
    private boolean success;
    private List<Float> emb;

    private String score;

    public SpeakerResponse(boolean success, List<Float> emb) {
        this.success = success;
        this.emb = emb;
    }

    public SpeakerResponse(boolean success, String score) {
        this.success = success;
        this.score = score;
    }

    public SpeakerResponse(boolean success) {
        this.score = score;
    }

    public SpeakerResponse(String score) {
        this.score = score;
    }
}

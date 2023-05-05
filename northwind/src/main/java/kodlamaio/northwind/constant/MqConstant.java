package kodlamaio.northwind.constant;

/**
 * Rabbitmq constant
 */
public class MqConstant {

    public static final String SPEECH_EXCHANGE = "ses.speech";

    public static final String SPEECH_ALL_QUEUE = "ses.speech.all";

    public static final String SPEECH_ALL_ROUTING_KEY = "*";

    public static final String SPEECH_EN_QUEUE = "ses.speech.english";

    public static final String SPEECH_EN_ROUTING_KEY = "english";

    public static final String SPEAKER_ENROLL_EXCHANGE = "ses.speaker.enroll";

    public static final String SPEAKER_ENROLL_QUEUE = "ses.speaker.enroll.queue";

    public static final String SPEAKER_RECOGNITION_EXCHANGE = "ses.speaker.recognition";

    public static final String SPEAKER_RECOGNITION_QUEUE = "ses.speaker.recognition.queue";

}

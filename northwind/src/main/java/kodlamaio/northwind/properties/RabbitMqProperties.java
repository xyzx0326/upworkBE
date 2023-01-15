package kodlamaio.northwind.properties;

public class RabbitMqProperties {
    private String host;
    private String username;
    private String password;
    private String queue;
    private String exchange;
    private String routingKey;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getQueue() {
        return queue;
    }
    public void setQueue(String queue) {
        this.queue = queue;
    }
    public String getExchange() {
        return exchange;
    }
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    public String getRoutingKey() {
        return routingKey;
    }
    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

//tbk

//    @Configuration
//    @ConfigurationProperties(prefix = "service.rabbitmq")
//    @Data
//    public class RabbitMqProperties {
//        private String middlewareInternalSpeakerRecognitionQueue;
//        private String middlewareInternalSpeechToTextQueue;
//        private String middlewareStatusQueue;
//        private String middlewareDbQueue;
//        private String middlewareExchange;
//        private String middlewareInternalSpeechToTextRouteKey;
//        private String middlewareInternalSpeakerRecognitionRouteKey;
//        private String middlewareStatusRouteKey;
//        private String middlewareDbRouteKey;
//        private String speechToTextRecognitionQueue;
//        private String speechToTextExchange;
//        private String speechToTextRecognitionRouteKey;
//        private String speakerRecognitionQueue;
//        private String speakerRecognitionExchange;
//        private String speakerRecognitionRouteKey;
//    }
//}
}

package kodlamaio.northwind.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@AllArgsConstructor
//@Getter
public class RabbitMqConfig {
//    private final ConnectionFactory connectionFactory;
//    private RabbitAdmin rabbitAdmin;
//    @Autowired
//    public RabbitMqConfig(ConnectionFactory connectionFactory) {
//        this.connectionFactory = connectionFactory;
//    }
//
//    @PostConstruct
//    public void init() {
//        rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.declareExchange(new DirectExchange("speech-recognition-exchange"));
//        rabbitAdmin.declareQueue(new Queue("speech-recognition-queue"));
//        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("speech-recognition-queue")).to(new DirectExchange("speech-recognition-exchange")).with("speech-recognition-routing-key"));
//    }
//
//    @PostConstruct
//    public void createQueue() {
//        rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.declareExchange(new DirectExchange("speech-recognition-exchange"));
//        rabbitAdmin.declareQueue(new Queue("recognized-text-queue"));
//        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("recognized-text-queue")).to(new DirectExchange("speech-recognition-exchange")).with("speech-recognition-routing-key"));
//    }
//
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory);
//    }

}

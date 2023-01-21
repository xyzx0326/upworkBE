package kodlamaio.northwind.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
//@AllArgsConstructor
//@Getter
public class RabbitMqConfig {
    private final ConnectionFactory connectionFactory;
    private RabbitAdmin rabbitAdmin;
    @Autowired
    public RabbitMqConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void init() {
        rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(new DirectExchange("speech-recognition-exchange"));
        rabbitAdmin.declareQueue(new Queue("speech-recognition-queue"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("speech-recognition-queue")).to(new DirectExchange("speech-recognition-exchange")).with("speech-recognition-routing-key"));
    }

    @PostConstruct
    public void createQueue() {
        rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(new DirectExchange("recognized-text-exchange"));
        rabbitAdmin.declareQueue(new Queue("recognized-text-queue"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("recognized-text-queue")).to(new DirectExchange("recognized-text-exchange")).with("recognized-text-routing-key"));
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory);
    }

}

package kodlamaio.northwind.config;

import kodlamaio.northwind.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Bean
    public TopicExchange speechToTextExchange() {
        return new TopicExchange(MqConstant.SPEECH_EXCHANGE, true, false);
    }

    @Bean
    public Queue speechToTextAllQueue() {
        return new Queue(MqConstant.SPEECH_ALL_QUEUE, true, false, false);
    }

    @Bean
    public Binding speechToTextAllBinding() {
        return BindingBuilder.bind(speechToTextAllQueue()).to(speechToTextExchange())
                .with(MqConstant.SPEECH_ALL_ROUTING_KEY);
    }

    @Bean
    public Queue speechToTextEnglishQueue() {
        return new Queue(MqConstant.SPEECH_EN_QUEUE, true, false, false);
    }

    @Bean
    public Binding speechToTextEnglishBinding() {
        return BindingBuilder.bind(speechToTextEnglishQueue()).to(speechToTextExchange())
                .with(MqConstant.SPEECH_EN_ROUTING_KEY);
    }

}

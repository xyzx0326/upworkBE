package kodlamaio.northwind.config;

import kodlamaio.northwind.constant.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // consumer number
        factory.setConcurrentConsumers(2);
        factory.setMaxConcurrentConsumers(2);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

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

package com.shaurya.activityService.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    // Creates a queue named activity.queue
    @Bean
    public Queue activityQueue() {
        return (Queue) QueueBuilder.durable("activityQueue").build();
    }

}


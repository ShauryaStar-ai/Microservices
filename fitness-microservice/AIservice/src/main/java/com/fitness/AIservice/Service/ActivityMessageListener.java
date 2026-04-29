package com.fitness.AIservice.Service;

import com.fitness.AIservice.DTO.ActiviyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActivityMessageListener {
    // this will be the consumer message

    @RabbitListener(queues = "activity")
    public void processActivty(ActiviyDTO actiivty){
        log.info("-----Recivied Activity For Processing----"+actiivty.getId());

    }
}

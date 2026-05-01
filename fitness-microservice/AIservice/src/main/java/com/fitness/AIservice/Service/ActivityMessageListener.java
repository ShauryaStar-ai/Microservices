package com.fitness.AIservice.Service;

import com.fitness.AIservice.DTO.ActiviyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ActivityMessageListener {

    @Autowired
    ActivityAIService activityAIService;

    @RabbitListener(queues = "activity")

    public void processActivity(ActiviyDTO activity) {

        log.info("Received Activity For Processing: {}", activity.getId());

        String aiResponse = activityAIService.generateRecommendaion(activity);
        // now this needs to be paresd as this full JSON
        // we only need content from this 
        System.out.println(aiResponse);
    }
}

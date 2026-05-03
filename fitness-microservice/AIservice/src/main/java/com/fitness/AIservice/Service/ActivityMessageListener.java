package com.fitness.AIservice.Service;

import com.fitness.AIservice.DTO.ActiviyDTO;
import com.fitness.AIservice.Entity.Recommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ActivityMessageListener {
    @Autowired
    RecommendationService recommendationService;
    @Autowired
    ActivityAIService activityAIService;

    @RabbitListener(queues = "activity")

    public void processActivity(ActiviyDTO activity) {

        log.info("Received Activity For Processing: {}", activity.getId());

        String aiResponse = activityAIService.generateRecommendaion(activity);

        // now this needs to be paresd as this full JSON we only need content from this

        processAIResponce(activity,aiResponse);

    }
    public void processAIResponce(ActiviyDTO activiy, String aiResponse){
        Recommendation recommendation = new Recommendation();
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(aiResponse); // makes a navigateable JSON tree
            // using path instead of get because path can return empty but get might return null leading to null pointer
            JsonNode contentNode = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content");

            // The content is a string that needs to be parsed again
            JsonNode recommendationJson = mapper.readTree(contentNode.asText());
            // for debugging
            log.info("Activty Recommendation parsed {}",recommendationJson.toString());

            // building the recommendation object and saving it
            recommendation.setActivityId(activiy.getId());
            recommendation.setUserId(activiy.getUserId());
            recommendation.setType(activiy.getType());
            recommendationJson.path("analysis").asText();



            List<String> improvements = new ArrayList<>();
            for (JsonNode node : recommendationJson.path("improvements")) {
                String text = node.path("recommendation").asText();
                improvements.add(text);
            }
            recommendation.setImprovements(improvements);

            List<String> suggestions = new ArrayList<>();
            for (JsonNode node : recommendationJson.path("suggestions")) {
                String text = node.path("description").asText();
                suggestions.add(text);
            }
            recommendation.setSugessions(suggestions); // Note: typo in entity name 'sugessions'

            List<String> safety = new ArrayList<>();
            for (JsonNode node : recommendationJson.path("safety")) {
                safety.add(node.asText());
            }
            recommendation.setSafety(safety);
            recommendationService.saveRecommendation(recommendation);



        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}

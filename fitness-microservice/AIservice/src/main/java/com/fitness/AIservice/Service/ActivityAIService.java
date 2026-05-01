package com.fitness.AIservice.Service;

import com.fitness.AIservice.DTO.ActiviyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ActivityAIService {
    @Autowired
    OpenAIService openAIService;


    public String generateRecommendaion(ActiviyDTO activity){
        String prompt = createPromptForActivity(activity);

        String response = openAIService.askGPT(prompt);

        return response;
    }

    private String createPromptForActivity(ActiviyDTO activity) {
        return String.format(
                "Analyze this fitness activity and give improvement advice:\n" +
                        "- Type: %s\n" +
                        "- Duration: %d minutes\n" +
                        "- Calories Burnt: %d\n" +
                        "- Start Time: %s\n" +
                        "- Additional Metrics: %s\n\n" +
                        "Provide:\n" +
                        "1. Performance analysis\n" +
                        "2. Improvement suggestions\n" +
                        "3. Safety tips\n",
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurnt(),
                activity.getStartTime(),
                activity.getAdditionalMatrics()
        );
    }
}

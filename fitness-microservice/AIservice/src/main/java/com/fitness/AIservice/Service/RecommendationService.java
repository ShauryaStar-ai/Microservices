package com.fitness.AIservice.Service;

import com.fitness.AIservice.DTO.ActiviyDTO;
import com.fitness.AIservice.Entity.Recommendation;
import com.fitness.AIservice.Repositroy.RecommendationRepo;
import org.bson.types.ObjectId;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    @Autowired
    RecommendationRepo recommendationRepo;

    @Autowired 
    WebClient activityServiceWebClient;
    // get activity by id from mongoDB
    public Optional<ActiviyDTO> getActivity(ObjectId activityId) {
        try {
            return activityServiceWebClient.get()
                    .uri("/api/activities/activity/{id}", activityId.toHexString())
                    .retrieve()
                    .bodyToMono(ActiviyDTO.class)
                    .blockOptional();
        } catch (WebClientResponseException e) {
            System.err.println("Error calling activityService: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return Optional.empty();
        }
    }

    // use mongo repo
    public  List<Recommendation> getUserRecommendation(Long userid) {
    return recommendationRepo.findByuserId(userid);
    }
    // use mongo repo
    public Recommendation getActivityRecommendation(ObjectId objectId) {
        return recommendationRepo.findByactivityId(objectId)
                .orElse(null);
    }
}

package com.fitness.AIservice.Controller;

import com.fitness.AIservice.DTO.ActiviyDTO;
import com.fitness.AIservice.Entity.Recommendation;
import com.fitness.AIservice.Service.RecommendationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{activityId}")
    public ResponseEntity<ActiviyDTO> getActivityForRecommendation(@PathVariable String activityId){
        if (!ObjectId.isValid(activityId)) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<ActiviyDTO> activityDTO = recommendationService.getActivity(new ObjectId(activityId));
        return activityDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable Long userid){
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userid));
    }
    @GetMapping("/activity/{activityid}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityid){

        Recommendation recommendation =
                recommendationService.getActivityRecommendation(new ObjectId(activityid));

        if (recommendation == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recommendation);
    }



}

package com.fitness.AIservice.Repositroy;

import com.fitness.AIservice.Entity.Recommendation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecommendationRepo extends MongoRepository<Recommendation, ObjectId> {
    List<Recommendation> findByuserId(Long userid);

    Optional<Recommendation> findByactivityId(ObjectId activityId);
}

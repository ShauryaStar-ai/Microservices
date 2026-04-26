package com.shaurya.activityService.Repository;

import com.shaurya.activityService.Entity.Activity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends MongoRepository<Activity,ObjectId> {
    List<Activity> findByUserId(long userId);
}

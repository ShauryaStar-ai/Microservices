package com.fitness.AIservice.DTO;

import com.fitness.AIservice.Entity.ActivityType; // Assuming ActivityType is also available or copied
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActiviyDTO {
    private String id;
    private long userId;
    private ActivityType Type;
    private Integer duration;
    private Integer caloriesBurnt;
    private LocalDateTime startTime;
    private Map<String,Object> additionalMatrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

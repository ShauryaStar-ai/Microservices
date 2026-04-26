package com.shaurya.activityService.DTO;

import com.shaurya.activityService.Entity.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
// there is nothing that neededs to be kept secrect so thats why it is the same as DTO
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

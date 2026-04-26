package com.shaurya.activityService.DTO;

import com.shaurya.activityService.Entity.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class ActivityRequest {
    private long userId;
    private ActivityType Type;
    private Integer duration;
    private Integer caloriesBurnt;
    private LocalDateTime startTime;
    private Map<String,Object> additionalMatrics;

}

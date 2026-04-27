package com.shaurya.activityService.Service;

import com.shaurya.activityService.DTO.ActivityRequest;
import com.shaurya.activityService.DTO.ActiviyDTO;
import com.shaurya.activityService.Entity.Activity;
import com.shaurya.activityService.Repository.ActivityRepo;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    @Autowired
    ActivityRepo activityRepo;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserValidationService userValidationService;
    @Value("${rabbitmq.exchange.name}")
private String exhange;
    @Value("${rabbitmq.exchange.key}")
private String routingkey;
    public ActiviyDTO trackActivity(ActivityRequest request){

        // checking if user exists or not
        boolean isuservalid = userValidationService.validateUser(request.getUserId());
        if(!isuservalid){
            throw new RuntimeException("The user is invalid "+request.getUserId());
        }
        // building activity object from the request and saving it
        Activity activityBuiltFromRequest = Activity.builder().userId(request.getUserId())
                .Type(request.getType())
                .duration(request.getDuration())
                .caloriesBurnt(request.getCaloriesBurnt())
                .startTime(request.getStartTime())
                .additionalMatrics(request.getAdditionalMatrics())
                .build();
        Activity saved = activityRepo.save(activityBuiltFromRequest);
        // publish to rabbit mq for processing
        try{

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mapToActivityDTO(saved);
    }

    private ActiviyDTO mapToActivityDTO(Activity activity){
        ActiviyDTO activiyDTO = new ActiviyDTO();
        activiyDTO.setId(activity.getId() != null ? activity.getId().toHexString() : null);
        activiyDTO.setUserId(activity.getUserId());
        activiyDTO.setType(activity.getType());
        activiyDTO.setDuration(activity.getDuration());
        activiyDTO.setCaloriesBurnt(activity.getCaloriesBurnt());
        activiyDTO.setStartTime(activity.getStartTime());
        activiyDTO.setAdditionalMatrics(activity.getAdditionalMatrics());
        activiyDTO.setCreatedAt(activity.getCreatedAt());
        activiyDTO.setUpdatedAt(activity.getUpdatedAt());
        return activiyDTO;
    }

    public List<ActiviyDTO> getAllActivitesOfUser(String userId) {
        try {
            long uId = Long.parseLong(userId);
            List<Activity> activities = activityRepo.findByUserId(uId);
            return activities.stream()
                    .map(this::mapToActivityDTO)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    public ActiviyDTO getActivityById(String id) {
        try {
            return activityRepo.findById(new ObjectId(id))
                    .map(this::mapToActivityDTO)
                    .orElse(null);
        } catch (IllegalArgumentException e) {
            // Returns null if the provided ID is not a valid ObjectId format
            return null;
        }
    }

    public List<ActiviyDTO> getAllActivities() {
        List<Activity> activities = activityRepo.findAll();
        return activities.stream()
                .map(this::mapToActivityDTO)
                .collect(Collectors.toList());
    }
}

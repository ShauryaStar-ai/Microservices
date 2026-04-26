package com.shaurya.activityService.Controller;

import com.shaurya.activityService.DTO.ActivityRequest;
import com.shaurya.activityService.DTO.ActiviyDTO;
import com.shaurya.activityService.Service.ActivityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActiviyDTO> trackActivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping("/userAllEntries/{id}")
    public ResponseEntity<List<ActiviyDTO>> getAllActivitiesOfaUser(@PathVariable String id){
        // find all activities of a user and the id sent here is the userId
        return ResponseEntity.ok(activityService.getAllActivitesOfUser(id));
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<ActiviyDTO> getActivityById(@PathVariable String id){
        ActiviyDTO activiyDTO = activityService.getActivityById(id);
        return activiyDTO != null ? ResponseEntity.ok(activiyDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllActivity")
    public ResponseEntity<List<ActiviyDTO>> getAllActivities(){
        return ResponseEntity.ok(activityService.getAllActivities());
    }

}

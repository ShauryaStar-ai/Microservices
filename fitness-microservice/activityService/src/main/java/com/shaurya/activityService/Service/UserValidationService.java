package com.shaurya.activityService.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserValidationService {
                private final WebClient userSeriviceWebclient;

    public boolean validateUser(Long userId) {
        try {
            return userSeriviceWebclient.get().uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            // Log the error or handle it as per your requirements
            return false;
        }
    }

}

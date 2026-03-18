package com.lcwd.user.service.external.services;

import com.lcwd.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="RATINGSERVICE")
public interface RatingService
{
    //get 
    
    //POST
    @PostMapping("/ratings")
    Rating createRating(Rating rating);
    
    //PUT 
    Rating updateRating();
}

package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService 
{
    //create
    Rating createRating(Rating rating);
    
    //get all ratings
    List<Rating> getAllRatings();
    
    //get all by userID
    List<Rating> getRatingsByUserId(String userId);
    
    //get all by hotel
    List<Rating> getRatingsByHotelId(String hotelId);
}

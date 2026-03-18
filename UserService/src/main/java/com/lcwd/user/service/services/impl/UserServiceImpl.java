package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelServcies;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelServcies hotelServcies;

    @Override
    public User saveUser(User user)
    {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId)
    {
        //get user from DB with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User given ID is" +
                " not found on DB" + userId));
        //fetch rating of the above user from RATINGSERVICE
        //From this URL
        //http://localhost:8083/ratings/users/2a72a62b-3a52-45fe-8173-99332049a0bd
        Rating[] ratingsOfUserArray =
                restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{}", ratingsOfUserArray);

        List<Rating> ratings = Arrays.stream(ratingsOfUserArray).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {

/*            ResponseEntity<Hotel> responseEntityHotel =
                    restTemplate.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = responseEntityHotel.getBody();*/
            Hotel hotel = hotelServcies.getHotel(rating.getHotelId());
            //logger.info("response status code {}", responseEntityHotel.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratings);


        return user;
    }
}

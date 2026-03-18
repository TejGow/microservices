package com.lcwd.hotel.services.impl;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.exceptions.ResourceNotFoundException;
import com.lcwd.hotel.respositories.HotelRepository;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService
{
    @Autowired
    private HotelRepository hotelRepository;
    
    @Override
    public Hotel create(Hotel hotel)
    {
        String randaomUid = UUID.randomUUID().toString();
        hotel.setId(randaomUid);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels()
    {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String hotelId)
    {
        return hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException());
    }
}

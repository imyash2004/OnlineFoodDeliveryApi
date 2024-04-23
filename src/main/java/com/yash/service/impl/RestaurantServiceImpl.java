package com.yash.service.impl;

import com.yash.dto.RestaurantDto;
import com.yash.model.Address;
import com.yash.model.Restaurant;
import com.yash.model.User;
import com.yash.repository.AddressRepo;
import com.yash.repository.RestaurantRepo;
import com.yash.repository.UserRepo;
import com.yash.request.CreateRestaurantRequest;
import com.yash.service.RestaurantService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address=addressRepo.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setOpeningHours(req.getOpeningHours());

        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant=restaurantRepo.findRestaurantById(restaurantId);
        if(updateRestaurant.getAddress()!=null){
            restaurant.setAddress(updateRestaurant.getAddress());
        }
        if(updateRestaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(updateRestaurant.getContactInformation()!=null){
            restaurant.setContactInformation(updateRestaurant.getContactInformation());
        }
        if(updateRestaurant.getName()!=null){
            restaurant.setName(updateRestaurant.getName());
        }
        if(updateRestaurant.getDescription()!=null){
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        return restaurantRepo.save(restaurant);

    }

    @Override
    public void deleteRestaurant(long restaurantId) throws Exception {
        Restaurant restaurant=restaurantRepo.findRestaurantById(restaurantId);
        restaurantRepo.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String prop) {
        return restaurantRepo.findBySearchQuery(prop);
    }

    @Override
    public Restaurant findRestaurantById(long id) throws Exception {
        Optional<Restaurant>restaurant=restaurantRepo.findById(id);
        if(restaurant.isEmpty()){
            throw new Exception("the restaurant is not available with this id ");
        }
        return restaurant.get();

    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepo.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("restaurant is not there with this userId");
        }
        return restaurant;

    }

    @Override
    public RestaurantDto addToFavourities(Long restaurantId, User user) throws Exception {
        Restaurant restaurant=restaurantRepo.findRestaurantById(restaurantId);
        RestaurantDto restaurantDto=new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setTitle(restaurant.getName());


        boolean x=false;
        for(RestaurantDto dtos:user.getFavorites()){
            if(dtos.getId().equals(restaurantDto.getId())){
                x=true;
                break;
            }
        }
        if(x){
            user.getFavorites().removeIf(fav->fav.getId().equals(restaurantId));
        }
        else{
            user.getFavorites().add(restaurantDto);
        }
//        if(user.getFavorites().contains(restaurantDto)){
//            user.getFavorites().remove(restaurantDto);
//
//        }
//        else user.getFavorites().add(restaurantDto);
        userRepo.save(user);
        return restaurantDto;

    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant=restaurantRepo.findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepo.save(restaurant);
    }
}

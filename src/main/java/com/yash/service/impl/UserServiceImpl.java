package com.yash.service.impl;

import com.yash.config.JwtProvider;
import com.yash.model.User;
import com.yash.repository.UserRepo;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserByJwt(String jwt) throws Exception {

        String email=jwtProvider.getEmailFromToken(jwt);
        User user=findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user=userRepo.findByEmail(email);
        if (user==null){
            throw new Exception("user not found...");
        }
        return user;
    }
}

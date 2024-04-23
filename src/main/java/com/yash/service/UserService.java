package com.yash.service;

import com.yash.model.User;

public interface UserService {
    public User findUserByJwt(String jwt)throws Exception;
    public User findUserByEmail(String email) throws Exception;
}

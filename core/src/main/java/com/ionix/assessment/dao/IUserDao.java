package com.ionix.assessment.dao;

import com.ionix.assessment.dto.request.UserRequest;
import com.ionix.assessment.dto.response.UserResponse;

import java.util.List;

public interface IUserDao {

    UserResponse save(UserRequest request);
    UserResponse findByEmail(String email);
    List<UserResponse> findAll();

    UserResponse deleteByEmail(String email);
}

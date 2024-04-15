package com.ionix.assessment.service;

import com.ionix.assessment.dto.request.UserRequest;
import com.ionix.assessment.dto.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface IUserService {
    UserResponse save(UserRequest request);
    UserResponse findByEmail(String email);
    List<UserResponse> findAll();

    UserResponse deleteByEmail(String email);

    Map externalSearch(String item);
}

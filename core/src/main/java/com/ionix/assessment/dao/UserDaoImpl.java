package com.ionix.assessment.dao;

import com.ionix.assessment.dto.request.UserRequest;
import com.ionix.assessment.dto.response.UserResponse;
import com.ionix.assessment.exceptions.UserAlreadyExistException;
import com.ionix.assessment.exceptions.UserNotFoundException;
import com.ionix.assessment.model.UserEntity;
import com.ionix.assessment.repository.IUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDaoImpl implements IUserDao{

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserResponse save(UserRequest request) {
        UserResponse response = new UserResponse();
        UserEntity entity = new UserEntity();

        BeanUtils.copyProperties(request, entity);

        UserEntity user = userRepository.findUserEntityByEmail(request.getEmail());

        if (user != null) {
            throw  new UserAlreadyExistException("User not found");
        }

        userRepository.save(entity);

        BeanUtils.copyProperties(entity, response);

        return response;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return createUserResponse(userRepository.findUserEntityByEmail(email));
    }

    @Override
    public List<UserResponse> findAll() {
        List<UserEntity> cards = userRepository.findAll();

        return cards.stream().map(this::createUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse deleteByEmail(String email) {
        UserResponse user = createUserResponse(userRepository.findUserEntityByEmail(email));

        if (user == null) {
            throw  new UserNotFoundException("User not found");
        }

        userRepository.deleteUserEntityByEmail(email);

        return user;
    }

    private UserResponse createUserResponse(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(entity.getId());
        userResponse.setName(entity.getName());
        userResponse.setUserName(entity.getUserName());
        userResponse.setUserName(entity.getUserName());
        userResponse.setEmail(entity.getEmail());
        userResponse.setPhone(entity.getPhone());
        return userResponse;
    }
}

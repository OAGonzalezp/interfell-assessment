package com.ionix.assessment.controller;

import com.ionix.assessment.dto.request.UserRequest;
import com.ionix.assessment.dto.response.DataResponse;
import com.ionix.assessment.dto.response.ResponseType;
import com.ionix.assessment.dto.response.UserResponse;
import com.ionix.assessment.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v0")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test(){

        return new ResponseEntity<>(ResponseType.OK, HttpStatus.OK);
    }

    @PostMapping(value = "/secure/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<UserResponse>> createUser(@RequestBody final UserRequest request){

        UserResponse response = userService.save(request);
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setCode(ResponseType.OK.getCode());
        dataResponse.setData(response);

        return new ResponseEntity<>(dataResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<List<UserResponse>>> getUsers(){

        List<UserResponse> response = userService.findAll();
        DataResponse<List<UserResponse>> dataResponse = new DataResponse<>();
        dataResponse.setCode(ResponseType.OK.getCode());
        dataResponse.setData(response);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<UserResponse>> getUserByEmail(@PathVariable String email){

        UserResponse response = userService.findByEmail(email);
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setCode(ResponseType.OK.getCode());
        dataResponse.setData(response);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/secure/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DataResponse<UserResponse>> deleteUserByEmail(@PathVariable String email){

        UserResponse response = userService.deleteByEmail(email);
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setCode(ResponseType.OK.getCode());
        dataResponse.setData(response);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/external/search/{param}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> externalSearch(@PathVariable String param){

        Map response = userService.externalSearch(param);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

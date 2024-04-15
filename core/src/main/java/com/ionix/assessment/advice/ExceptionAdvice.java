package com.ionix.assessment.advice;

import com.ionix.assessment.dto.response.DataResponse;
import com.ionix.assessment.dto.response.ResponseType;
import com.ionix.assessment.exceptions.UserAlreadyExistException;
import com.ionix.assessment.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionAdvice {

    @Autowired
    HttpServletRequest httpServletRequest;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<DataResponse> processUserNotFoundException(final Exception ex) {
        return new ResponseEntity(ResponseType.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseBody
    public ResponseEntity<DataResponse> processUserAlreadyExistException(final Exception ex) {
        return new ResponseEntity(ResponseType.USER_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
    }
}

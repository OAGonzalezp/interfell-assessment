package com.ionix.assessment.dto.response;

import com.ionix.assessment.model.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserResponse implements Serializable {

    public UserResponse() {
    }

    private Long id;
    private String name;
    private String userName;
    private String email;
    private String phone;
}

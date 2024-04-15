package com.ionix.assessment.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class UserRequest implements Serializable {
    private String name;
    private String userName;
    private String email;
    private String phone;
}

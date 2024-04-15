package com.ionix.assessment.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseMessage implements Serializable {
    private int code;
    private String message;
}

package com.ionix.assessment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseType {
    OK(1, "Ok"),
    USER_NOT_FOUND(-2, "User not found"),
    USER_ALREADY_EXIST(-3, "User already exist");

    private DataResponse data;
    @Getter
    private Integer code;
    @Getter
    private String message;
    @JsonValue
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final DataResponse value() {
        return data;
    }
    ResponseType(Integer code, String message) {
        this.data = new DataResponse<>(code, message);
        this.code = code;
        this.message = message;
    }
}

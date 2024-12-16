package com.example.coddingshuttle.RestFullApi.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse<T> {
    @JsonFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    public ApiResponse(T data) {
        this();
        this.data = data;
    }
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }
    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }

}

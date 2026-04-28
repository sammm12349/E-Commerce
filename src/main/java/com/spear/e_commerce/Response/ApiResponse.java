package com.spear.e_commerce.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ApiResponse {
    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    private String message;
    private Object data;
}

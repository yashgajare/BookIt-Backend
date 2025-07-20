package com.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponse {
    private String message;
    private Long messageId;

    public MessageResponse(String message) {
        this.message = message;
    }

}


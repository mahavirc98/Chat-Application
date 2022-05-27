package com.chat.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {
    private int id;
    private String message;

    private String timestamp;
    @JsonProperty("isSent")
    private boolean isSent;
    private String user;
}

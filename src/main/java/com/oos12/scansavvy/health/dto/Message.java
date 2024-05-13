package com.oos12.scansavvy.health.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Message {
    private String role;
    private String content;

    public Message(String user, String prompt) {
        this.role = user;
        this.content = prompt;
    }
}

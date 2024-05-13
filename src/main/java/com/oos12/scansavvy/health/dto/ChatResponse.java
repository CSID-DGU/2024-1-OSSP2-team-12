package com.oos12.scansavvy.health.dto;

import lombok.Getter;
import org.apache.catalina.WebResource;

import java.awt.*;
import java.util.List;
@Getter
public class ChatResponse {
    private List<Choice> choices;

    @Getter
    public static class Choice{
        private int index;
        private Message message;

    }
}

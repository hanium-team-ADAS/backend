package com.hanium.adas.domain.videochat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    private String sender;
    private String type;
    private Long roomId;
    private String data;
    private String user;
    private String receiver;
    private Object offer;
    private Object answer;
    private Object candidate;
    private Object sdp;
}

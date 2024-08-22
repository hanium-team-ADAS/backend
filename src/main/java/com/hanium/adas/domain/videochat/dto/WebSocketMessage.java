package com.hanium.adas.domain.videochat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebSocketMessage {
    private String sender;
    private String type;
    private Long roomId;
    private String data;
    private List<String> allUsers;
    private String receiver;
    private Object offer;
    private Object answer;
    private Object candidate;
    private Object sdp;
}

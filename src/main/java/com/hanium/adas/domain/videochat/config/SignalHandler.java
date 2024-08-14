package com.hanium.adas.domain.videochat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.adas.domain.videochat.dto.WebSocketMessage;
import com.hanium.adas.domain.videochat.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SignalHandler extends TextWebSocketHandler {

    private final SessionRepository sessionRepository = SessionRepository.getInstance();  // 세션 데이터 저장소
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String MSG_TYPE_JOIN_ROOM = "join_room";
    private static final String MSG_TYPE_OFFER = "offer";
    private static final String MSG_TYPE_ANSWER = "answer";
    private static final String MSG_TYPE_CANDIDATE = "candidate";

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        WebSocketMessage message = objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
        String userName = message.getSender();
        String data = message.getData();
        Long roomId = message.getRoomId();

        switch (message.getType()) {
            case MSG_TYPE_JOIN_ROOM:
                if(sessionRepository.hasRoom(roomId)) {
                    sessionRepository.addClient(roomId, session);
                } else {
                    sessionRepository.addClientInNewRoom(roomId, session);
                }

                sessionRepository.saveRoomIdToSession(session, roomId);

                String exportClient = "";
                for (Map.Entry<String, WebSocketSession> entry : sessionRepository.getClientList(roomId).entrySet()) {
                    if (entry.getValue() != session) {
                        exportClient = entry.getKey();
                    }
                }

                sendMessage(session,
                        new WebSocketMessage().builder()
                                .type("all_users")
                                .sender(userName)
                                .data(message.getData())
                                .user(exportClient)
                                .candidate(message.getCandidate())
                                .sdp(message.getSdp())
                                .build());
                break;

            case MSG_TYPE_OFFER:
            case MSG_TYPE_ANSWER:
            case MSG_TYPE_CANDIDATE:

                if (sessionRepository.hasRoom(roomId)) {
                    Map<String, WebSocketSession> clientList = sessionRepository.getClientList(roomId);
                    if (clientList.containsKey(message.getReceiver())) {
                        WebSocketSession ws = clientList.get(message.getReceiver());
                        sendMessage(ws,
                                new WebSocketMessage().builder()
                                        .type(message.getType())
                                        .sender(session.getId())            // 보낸사람 session Id
                                        .receiver(message.getReceiver())    // 받을사람 session Id
                                        .data(message.getData())
                                        .offer(message.getOffer())
                                        .answer(message.getAnswer())
                                        .candidate(message.getCandidate())
                                        .sdp(message.getSdp())
                                        .build());
                    }
                }
                break;

            default:
                log.info("======================================== DEFAULT");
                log.info("============== 들어온 타입 : " + message.getType());


        }

    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        // 웹소켓 연결이 끊어지면 실행되는 메소드
        log.info("======================================== 웹소켓 연결 해제 : {}", session.getId());
        // 끊어진 세션이 어느방에 있었는지 조회
        Long roomId = Optional.ofNullable(sessionRepository.getRoomId(session)).orElseThrow(
                () -> new IllegalArgumentException("해당 세션이 있는 방정보가 없음!")
        );

        // 1) 방 참가자들 세션 정보들 사이에서 삭제
        log.info("==========leave 1 : (삭제 전) Client List - \n {} \n", Optional.ofNullable(sessionRepository.getClientList(roomId)));
        sessionRepository.deleteClient(roomId, session);
        log.info("==========leave 2 : (삭제 후) Client List - \n {} \n", Optional.ofNullable(sessionRepository.getClientList(roomId)));


        // 2) 별도 해당 참가자 세션 정보도 삭제
        log.info("==========leave 3 : (삭제 전) roomId to Session - \n {} \n", Optional.ofNullable(sessionRepository.searchRoomIdToSession(roomId)));
        sessionRepository.deleteRoomIdToSession(session);
        log.info("==========leave 4 : (삭제 후) roomId to Session - \n {} \n", Optional.ofNullable(sessionRepository.searchRoomIdToSession(roomId)));


        // 본인 제외 모두에게 전달
        Map<String, WebSocketSession> clientList = Optional.ofNullable(sessionRepository.getClientList(roomId))
                .orElseThrow(
                        () -> new IllegalArgumentException("clientList 없음")
                );
        for(Map.Entry<String, WebSocketSession> oneClient : clientList.entrySet()){
            sendMessage(oneClient.getValue(),
                    new WebSocketMessage().builder()
                            .type("leave")
                            .sender(session.getId())
                            .receiver(oneClient.getKey())
                            .build());
        }

    }

    private void sendMessage(WebSocketSession session, WebSocketMessage message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            log.info("========== 발송 to : " + session.getId());
            log.info("========== 발송 내용 : " + json);
            session.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            log.info("============== 발생한 에러 메세지: " + e.getMessage());
        }
    }


}

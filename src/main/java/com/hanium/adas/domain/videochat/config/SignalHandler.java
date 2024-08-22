package com.hanium.adas.domain.videochat.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.adas.domain.videochat.dto.WebSocketMessage;
import com.hanium.adas.domain.videochat.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SignalHandler extends TextWebSocketHandler {

    private final SessionRepository sessionRepositoryRepo = SessionRepository.getInstance();  // 세션 데이터 저장소
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String MSG_TYPE_JOIN_ROOM = "join_room";
    private static final String MSG_TYPE_OFFER = "offer";
    private static final String MSG_TYPE_ANSWER = "answer";
    private static final String MSG_TYPE_CANDIDATE = "candidate";

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        log.info("웹소켓 연결 성공");
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) {

        try {
            WebSocketMessage message = objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
            String userName = message.getSender();
            String data = message.getData();
            Long roomId = message.getRoomId();

            log.info("======================================== origin message INFO");
            log.info("==========session.Id : {}, getType : {},  getRoomId :  {}", session.getId(), message.getType(), roomId.toString());

            switch (message.getType()) {
                case MSG_TYPE_JOIN_ROOM:

                    if (sessionRepositoryRepo.hasRoom(roomId)) {
                        log.info("==========join 0 : 방 있음 :" + roomId);
                        log.info("==========join 1 : (join 전) Client List - \n {} \n", Optional.ofNullable(sessionRepositoryRepo.getClientList(roomId)));

                        // 해당 챗룸이 존재하면
                        sessionRepositoryRepo.addClient(roomId, session);

                    } else {
                        log.info("==========join 0 : 방 없음 :" + roomId);
                        // 해당 챗룸이 존재하지 않으면
                        sessionRepositoryRepo.addClientInNewRoom(roomId, session);
                    }

                    log.info("==========join 2 : (join 후) Client List - \n {} \n", Optional.ofNullable(sessionRepositoryRepo.getClientList(roomId)));

                    sessionRepositoryRepo.saveRoomIdToSession(session, roomId);

                    log.info("==========join 3 : 지금 세션이 들어간 방 :" + Optional.ofNullable(sessionRepositoryRepo.getRoomId(session)));

                    List<String> exportClientList = new ArrayList<>();
                    for (Map.Entry<String, WebSocketSession> entry : sessionRepositoryRepo.getClientList(roomId).entrySet()) {
                        if (entry.getValue() != session) {
                            exportClientList.add(entry.getKey());
                        }
                    }

                    log.info("==========join 4 : allUsers로 Client List : {}", exportClientList);

                    // 접속한 본인에게 방안 참가자들 정보를 전송
                    sendMessage(session,
                            new WebSocketMessage().builder()
                                    .type("all_users")
                                    .sender(userName)
                                    .data(message.getData())
                                    .allUsers(exportClientList)
                                    .candidate(message.getCandidate())
                                    .sdp(message.getSdp())
                                    .build());

                    break;

                case MSG_TYPE_OFFER:
                case MSG_TYPE_ANSWER:
                case MSG_TYPE_CANDIDATE:

                    if (sessionRepositoryRepo.hasRoom(roomId)) {
                        Map<String, WebSocketSession> clientList = sessionRepositoryRepo.getClientList(roomId);

                        log.info("=========={} 5 : 보내는 사람 - {}, 받는 사람 - {}", message.getType(), session.getId(), message.getReceiver());

                        if (clientList.containsKey(message.getReceiver())) {
                            WebSocketSession ws = clientList.get(message.getReceiver());
                            sendMessage(ws,
                                    WebSocketMessage.builder()
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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        log.info("======================================== 웹소켓 연결 해제 : {}", session.getId());

        Long roomId = Optional.ofNullable(sessionRepositoryRepo.getRoomId(session)).orElseThrow(
                () -> new IllegalArgumentException("해당 세션이 있는 방정보가 없음!")
        );

        log.info("==========leave 1 : (삭제 전) Client List - \n {} \n", Optional.ofNullable(sessionRepositoryRepo.getClientList(roomId)));
        sessionRepositoryRepo.deleteClient(roomId, session);
        log.info("==========leave 2 : (삭제 후) Client List - \n {} \n", Optional.ofNullable(sessionRepositoryRepo.getClientList(roomId)));

        log.info("==========leave 3 : (삭제 전) roomId to Session - \n {} \n", Optional.ofNullable(sessionRepositoryRepo.searchRoomIdToSession(roomId)));
        sessionRepositoryRepo.deleteRoomIdToSession(session);
        log.info("==========leave 4 : (삭제 후) roomId to Session - \n {} \n", Optional.ofNullable(sessionRepositoryRepo.searchRoomIdToSession(roomId)));

        Map<String, WebSocketSession> clientList = Optional.ofNullable(sessionRepositoryRepo.getClientList(roomId))
                .orElseThrow(() -> new IllegalArgumentException("clientList 없음"));

        for (Map.Entry<String, WebSocketSession> oneClient : clientList.entrySet()) {
            sendMessage(oneClient.getValue(),
                    WebSocketMessage.builder()
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

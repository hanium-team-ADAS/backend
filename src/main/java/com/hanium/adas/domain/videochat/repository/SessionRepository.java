package com.hanium.adas.domain.videochat.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@NoArgsConstructor
// 의사의 ID를 방 ID로 사용
public class SessionRepository {

    private static SessionRepository sessionRepository;
    private final Map<Long, Map<String, WebSocketSession>> clientsInRoom = new HashMap<>(); // <Room ID, <Session ID, Session 객체>>
    private final Map<WebSocketSession, Long> roomIdToSession = new HashMap<>(); // <Seesion 객체, Room ID>

    // 싱글톤으로 구현
    public static SessionRepository getInstance(){
        if(sessionRepository == null){
            synchronized (SessionRepository.class){
                sessionRepository = new SessionRepository();
            }
        }
        return sessionRepository;
    }

    public Map<String, WebSocketSession> getClientList(Long roomId) {
        return clientsInRoom.get(roomId);
    }

    public boolean hasRoom(Long roomId) {
        return clientsInRoom.containsKey(roomId);
    }

    public void addClient(Long roomId, WebSocketSession session) {
        clientsInRoom.get(roomId).put(session.getId(), session);
    }

    public void addClientInNewRoom(Long roomId, WebSocketSession session) {
        Map<String, WebSocketSession> newClient = new HashMap<>();
        newClient.put(session.getId(), session);
        clientsInRoom.put(roomId, newClient);
    }

    public void saveRoomIdToSession(WebSocketSession session, Long roomId) {
        roomIdToSession.put(session, roomId);
    }

    public Long getRoomId(WebSocketSession session){
        return roomIdToSession.get(session);
    }

    public Map<WebSocketSession, Long> searchRoomIdToSession(Long roomId) {
        return Optional.of(roomIdToSession.entrySet()
                        .stream()
                        .filter(entry ->  entry.getValue() == roomId)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .orElseThrow(
                        () -> new IllegalArgumentException("RoodIdToSession 정보 없음!")
                );
    }

    public void deleteClient(Long roomId, WebSocketSession session) {
        Map<String, WebSocketSession> clientList = clientsInRoom.get(roomId);
        String removeKey = "";
        for(Map.Entry<String, WebSocketSession> oneClient : clientList.entrySet()){
            if(oneClient.getKey().equals(session.getId())){
                removeKey = oneClient.getKey();
            }
        }
        log.info("========== 지워질 session id : " + removeKey);
        clientList.remove(removeKey);

        // 끊어진 세션을 제외한 나머지 세션들을 다시 저장
        clientsInRoom.put(roomId, clientList);
    }


    public void deleteRoomIdToSession(WebSocketSession session) {
        roomIdToSession.remove(session);
    }



}

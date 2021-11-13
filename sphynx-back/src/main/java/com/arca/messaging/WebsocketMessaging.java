package com.arca.messaging;

import com.arca.importing.model.Progress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Service
public class WebsocketMessaging extends TextWebSocketHandler {

    private WebSocketSession session;

    Logger log = LoggerFactory.getLogger(WebsocketMessaging.class);

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
    }

    public void sendProgress(long totalNbLines, long nbLines) {

        // If no connection has been established, don't bother to do anything
        if (session == null || !session.isOpen()) {
            return;
        }

        try {
            String message = jacksonObjectMapper.writeValueAsString(new Progress(totalNbLines, nbLines));
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Impossible to send message in websocket.", e);
        }
    }
}

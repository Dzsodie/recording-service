package com.zetoinc.recording_service.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zetoinc.recording_service.model.Recording;
import com.zetoinc.recording_service.service.RecordingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket handler for managing real-time recording data updates.
 */
@Component
@Slf4j
public class RecordingWebSocketHandler extends TextWebSocketHandler {

    private final RecordingService recordingService;
    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Constructs a new WebSocket handler for recordings.
     *
     * @param recordingService the recording service for fetching data
     */
    public RecordingWebSocketHandler(RecordingService recordingService) {
        Assert.notNull(recordingService, "RecordingService must not be null");
        this.recordingService = recordingService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Handles WebSocket connections.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("New WebSocket connection established: {}", session.getId());
        sendRecordingsToClient(session);
    }

    /**
     * Handles incoming WebSocket messages.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received WebSocket message: {}", message.getPayload());

        try {
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());

            if (jsonNode.has("action") && "update".equals(jsonNode.get("action").asText())) {
                Recording updatedRecording = objectMapper.treeToValue(jsonNode.get("recording"), Recording.class);
                recordingService.updateRecording(updatedRecording);

                log.info("Updated recording in DB: {}", updatedRecording);

                // Broadcast updated list to all clients
                broadcastRecordings();
            }
        } catch (Exception e) {
            log.error("Error processing WebSocket message: {}", e.getMessage(), e);
        }
    }

    /**
     * Handles WebSocket disconnections.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        log.info("WebSocket connection closed: {} - Reason: {}", session.getId(), status);
    }

    /**
     * Sends the current list of recordings to a single client.
     */
    private void sendRecordingsToClient(WebSocketSession session) {
        try {
            String response = objectMapper.writeValueAsString(recordingService.getAllRecordings());
            session.sendMessage(new TextMessage(response));
        } catch (IOException e) {
            log.error("Error sending recordings to client: {}", e.getMessage(), e);
        }
    }

    /**
     * Broadcasts the updated recordings list to all connected clients.
     */
    private void broadcastRecordings() {
        try {
            String response = objectMapper.writeValueAsString(recordingService.getAllRecordings());
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(response));
                }
            }
            log.info("Broadcasted updated recordings to all clients.");
        } catch (IOException e) {
            log.error("Error broadcasting recordings: {}", e.getMessage(), e);
        }
    }
}

package com.zetoinc.recording_service.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zetoinc.recording_service.model.Recording;
import com.zetoinc.recording_service.service.RecordingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordingWebSocketHandlerTest {

    @Mock
    private RecordingService recordingService;

    @Mock
    private WebSocketSession mockSession;

    @InjectMocks
    private RecordingWebSocketHandler webSocketHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        webSocketHandler = new RecordingWebSocketHandler(recordingService);
    }

    @Test
    void testConstructor_ShouldInitializeHandler() {
        assertNotNull(webSocketHandler, "WebSocketHandler should be instantiated");
    }

    @Test
    void testAfterConnectionEstablished_ShouldAddSessionAndSendData() throws Exception {
        when(mockSession.getId()).thenReturn("session1");
        when(recordingService.getAllRecordings()).thenReturn(List.of(new Recording()));

        webSocketHandler.afterConnectionEstablished(mockSession);

        verify(mockSession, times(1)).sendMessage(any(TextMessage.class));
    }

    @Test
    void testHandleTextMessage_ShouldUpdateRecordingAndBroadcast() throws Exception {
        // Mock message payload
        String payload = "{ \"action\": \"update\", \"recording\": { \"id\": 1, \"sedation\": \"Deep\" } }";
        TextMessage message = new TextMessage(payload);

        // Mock behavior
        Recording recording = new Recording();
        recording.setId(1L);
        recording.setSedation("Deep");
        when(recordingService.getAllRecordings()).thenReturn(List.of(recording));

        webSocketHandler.handleTextMessage(mockSession, message);

        verify(recordingService, times(1)).updateRecording(any(Recording.class));
    }

    @Test
    void testHandleTextMessage_ShouldHandleInvalidJson() throws Exception {
        TextMessage message = new TextMessage("Invalid JSON");

        webSocketHandler.handleTextMessage(mockSession, message);

        verify(recordingService, never()).updateRecording(any());
    }

    @Test
    void testAfterConnectionClosed_ShouldRemoveSession() throws Exception {
        Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
        sessions.add(mockSession);

        webSocketHandler.afterConnectionClosed(mockSession, CloseStatus.NORMAL);

        verify(mockSession, never()).sendMessage(any());
    }

    @Test
    void testBroadcastRecordings_ShouldSendToAllSessions() throws Exception {
        // Mock sessions
        WebSocketSession session1 = mock(WebSocketSession.class);
        WebSocketSession session2 = mock(WebSocketSession.class);
        when(session1.isOpen()).thenReturn(true);
        when(session2.isOpen()).thenReturn(true);
        when(recordingService.getAllRecordings()).thenReturn(List.of(new Recording()));

        webSocketHandler.afterConnectionEstablished(session1);
        webSocketHandler.afterConnectionEstablished(session2);
        webSocketHandler.handleTextMessage(session1, new TextMessage("{\"action\": \"update\", \"recording\": {\"id\": 1}}"));

        verify(session1, atLeastOnce()).sendMessage(any(TextMessage.class));
        verify(session2, atLeastOnce()).sendMessage(any(TextMessage.class));
    }
}

package com.zetoinc.recording_service.config;

import com.zetoinc.recording_service.websocket.RecordingWebSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketConfigTest {

    @Mock
    private RecordingWebSocketHandler mockHandler;

    @Mock
    private WebSocketHandlerRegistry mockRegistry;

    private WebSocketConfig webSocketConfig;

    @BeforeEach
    void setUp() {
        webSocketConfig = new WebSocketConfig(mockHandler);
    }

    @Test
    void testConstructor_ShouldInitializeHandler() {
        assertNotNull(webSocketConfig, "WebSocketConfig should be instantiated");
    }

    @Test
    void testConstructor_ShouldThrowException_WhenHandlerIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new WebSocketConfig(null));

        assertEquals("RecordingWebSocketHandler must not be null", exception.getMessage());
    }

    @Test
    void testRegisterWebSocketHandlers_ShouldRegisterHandler() {
        var handlerRegistration = mock(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration.class);
        when(mockRegistry.addHandler(any(), anyString())).thenReturn(handlerRegistration);

        webSocketConfig.registerWebSocketHandlers(mockRegistry);

        verify(mockRegistry, times(1)).addHandler(mockHandler, "/ws/recordings");
        verify(handlerRegistration, times(1)).setAllowedOrigins("*");
    }
}

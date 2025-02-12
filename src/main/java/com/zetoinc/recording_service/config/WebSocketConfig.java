package com.zetoinc.recording_service.config;

import com.zetoinc.recording_service.websocket.RecordingWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.util.Assert;

/**
 * WebSocket configuration class responsible for registering WebSocket handlers.
 */
@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    private final RecordingWebSocketHandler handler;

    /**
     * Constructor to initialize the WebSocket handler.
     *
     * @param handler the WebSocket handler for managing recording data
     * @throws IllegalArgumentException if handler is null
     */
    public WebSocketConfig(RecordingWebSocketHandler handler) {
        Assert.notNull(handler, "RecordingWebSocketHandler must not be null");
        this.handler = handler;
    }

    /**
     * Registers WebSocket handlers for the application.
     *
     * @param registry the WebSocket handler registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("Registering WebSocket handler at /ws/recordings");
        registry.addHandler(handler, "/ws/recordings").setAllowedOrigins("*");
    }
}

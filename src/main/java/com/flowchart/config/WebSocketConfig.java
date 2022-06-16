package com.flowchart.config;

import com.flowchart.server.WebSocketServer;
import com.flowchart.service.OnlineFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @className: WebSocketConfig
 * @Description: 开启WebSocket支持
 * @author: ct
 * @date: 2022/2/23 14:17
 */
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter 作用
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setOnlineFileService(OnlineFileService onlineFileService) {
        WebSocketServer.onlineFileService = onlineFileService;
    }
}

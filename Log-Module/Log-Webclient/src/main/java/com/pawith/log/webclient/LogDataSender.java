package com.pawith.log.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class LogDataSender {

    private final WebClient webClient;

    public LogDataSender(WebClient.Builder webClientBuilder, @Value("${log.server.base-url}") String logServerBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(logServerBaseUrl).build();
    }

    public void sendLogData(SaveLogRequest saveLogRequest) {
        webClient.post()
                .uri("/api/log")
                .bodyValue(saveLogRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
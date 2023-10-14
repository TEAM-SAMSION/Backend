package com.pawith.authapplication.handler;

import com.pawith.authapplication.handler.request.UnusedTokenExpireEvent;
import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.authdomain.service.TokenQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class UnusedTokenExpireEventHandler {

    private final TokenQueryService tokenQueryService;
    private final TokenDeleteService tokenDeleteService;

    @EventListener
    public void handle(UnusedTokenExpireEvent event){
        final List<Token> allToken = tokenQueryService.findAllByEmailAndTokenType(event.getEmail(), event.getTokenType());
        tokenDeleteService.deleteAllToken(allToken);
    }

}

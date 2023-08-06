package com.petmory.authmodule.application.port.out.observer.subject;

import com.petmory.authmodule.application.dto.OAuthRequest;
import com.petmory.authmodule.application.dto.OAuthResponse;
import com.petmory.authmodule.application.dto.OAuthUserInfo;
import com.petmory.authmodule.application.port.out.observer.observer.AbstractOAuthObserver;
import com.petmory.commonmodule.observer.Observer;
import com.petmory.commonmodule.subject.Status;
import com.petmory.commonmodule.subject.Subject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public final class AbstractOAuthSubject implements Subject<OAuthRequest, OAuthResponse> {
    private final List<AbstractOAuthObserver> observerList = new ArrayList<>();

    @Override
    public void registerObserver(Observer<? extends Status,?> o) {
        observerList.add((AbstractOAuthObserver) o);
    }

    @Override
    public void removeObserver(Observer<? extends Status, ?> o) {
        observerList.remove((AbstractOAuthObserver) o);
    }

    @Override
    public OAuthResponse notifyObservers(OAuthRequest object) {
        final OAuthUserInfo oAuthUserInfo = attemptLogin(new OAuth(object.getProvider(), object.getAccessToken()));
        return generateServerAccessTool(oAuthUserInfo);
    }

    private OAuthUserInfo attemptLogin(OAuth oAuth) {
        for (AbstractOAuthObserver abstractOAuthObserver : observerList) {
            if (abstractOAuthObserver.isLogin(oAuth.getProvider())) {
                return abstractOAuthObserver.accept(oAuth);
            }
        }
        throw new IllegalArgumentException("로그인 실패");
    }

    private OAuthResponse generateServerAccessTool(OAuthUserInfo oAuthUserInfo) {
        return new OAuthResponse(oAuthUserInfo.getEmail()+" "+oAuthUserInfo.getUsername());
    }

    public void initObserver(ApplicationContext applicationContext){
        applicationContext.getBeansOfType(AbstractOAuthObserver.class)
                .values()
                .forEach(this::registerObserver);
    }
}

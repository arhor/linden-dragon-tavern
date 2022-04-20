package com.github.arhor.linden.dragon.tavern.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import dev.arhor.simple.todo.service.event.UserLoadedEvent;

@ExtendWith(MockitoExtension.class)
class DelegatingOAuth2UserServiceImplTest {

    @Mock private ApplicationEventPublisher applicationEventPublisher;
    @Mock private OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;
    @Mock private OAuth2User expectedUser;
    @Mock private OAuth2UserRequest userRequest;

    @InjectMocks private DelegatingOAuth2UserServiceImpl userService;

    @Test
    void setDelegate() {
        // given
        when(delegate.loadUser(userRequest)).thenReturn(expectedUser);

        // when
        var actualUser = userService.loadUser(userRequest);

        // then
        assertThat(actualUser).isEqualTo(expectedUser);

        verify(delegate).loadUser(userRequest);
        verify(applicationEventPublisher).publishEvent(any(UserLoadedEvent.class));
    }
}

package com.github.arhor.linden.dragon.tavern.service.impl;

import com.github.arhor.linden.dragon.tavern.service.event.UserLoadedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class DelegatingOAuth2UserServiceImpl(
    private val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    constructor(applicationEventPublisher: ApplicationEventPublisher) : this(
        delegate = DefaultOAuth2UserService(),
        applicationEventPublisher = applicationEventPublisher,
    )

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        return delegate.loadUser(userRequest).also {
            applicationEventPublisher.publishEvent(
                UserLoadedEvent(it)
            )
        }
    }
}

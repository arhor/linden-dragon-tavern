package com.github.arhor.linden.dragon.tavern.web.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@ExtendWith(MockitoExtension.class)
class OwnerResolverImplTest {

    private OwnerResolverImpl ownerResolver;

    @BeforeEach
    void setUp() {
        ownerResolver = new OwnerResolverImpl();
    }

    @Test
    @DisplayName("resolveOwnerId should return string containing authorized client registration id and principal name")
    void resolveOwnerId_oAuth2AuthenticationToken_ownerId(@Mock OAuth2AuthenticationToken oAuth2Token) {
        // given
        var registrationId = RandomString.make();
        var username = RandomString.make();

        when(oAuth2Token.getAuthorizedClientRegistrationId()).thenReturn(registrationId);
        when(oAuth2Token.getName()).thenReturn(username);

        // when
        var ownerId = ownerResolver.resolveOwnerId(oAuth2Token);

        // then
        assertThat(ownerId)
            .isNotNull()
            .contains(registrationId, username);
    }

    @Test
    @DisplayName("resolveOwnerId should return string equal to principal name")
    void resolveOwnerId_authentication_ownerId(@Mock Authentication authentication) {
        // given
        var username = RandomString.make();

        when(authentication.getName()).thenReturn(username);

        // when
        var ownerId = ownerResolver.resolveOwnerId(authentication);

        // then
        assertThat(ownerId)
            .isNotNull()
            .isEqualTo(username);
    }
}

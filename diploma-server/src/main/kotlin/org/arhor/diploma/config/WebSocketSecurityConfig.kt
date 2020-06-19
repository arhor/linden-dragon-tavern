package org.arhor.diploma.config

import org.arhor.diploma.BasicAuthorities
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer

@Configuration
class WebSocketSecurityConfig : AbstractSecurityWebSocketMessageBrokerConfigurer() {
  override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
    messages
        .nullDestMatcher().authenticated()
        .simpDestMatchers("/topic/tracker").hasAuthority(BasicAuthorities.ADMIN.role)
        // matches any destination that starts with /topic/
        // (i.e. cannot send messages directly to /topic/)
        // (i.e. cannot subscribe to /topic/messages/* to get messages sent to
        // /topic/messages-user<id>)
        .simpDestMatchers("/topic/**").authenticated()

        // message types other than MESSAGE and SUBSCRIBE
        .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll() // catch all
        .anyMessage().denyAll()
  }

  /**
   * Disables CSRF for web-sockets.
   */
  override fun sameOriginDisabled(): Boolean {
    return true
  }
}
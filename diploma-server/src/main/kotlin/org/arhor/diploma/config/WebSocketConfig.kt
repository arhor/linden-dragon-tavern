package org.arhor.diploma.config

import org.arhor.diploma.config.properties.CustomProperties
import org.arhor.diploma.util.BasicAuthorities.ANONYMOUS
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(private val props: CustomProperties) : WebSocketMessageBrokerConfigurer {

  override fun configureMessageBroker(config: MessageBrokerRegistry) {
    config.enableSimpleBroker("/topic")
  }

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    val allowedOrigins = props.cors.allowedOrigins?.toTypedArray() ?: arrayOf()

    registry.addEndpoint("/websocket/tracker")
        .setHandshakeHandler(defaultHandshakeHandler())
        .setAllowedOrigins(*allowedOrigins)
        .withSockJS()
        .setInterceptors(httpSessionHandshakeInterceptor())
  }

  @Bean
  fun httpSessionHandshakeInterceptor(): HandshakeInterceptor {
    return object : HandshakeInterceptor {

      @Throws(Exception::class)
      override fun beforeHandshake(
          request: ServerHttpRequest,
          response: ServerHttpResponse,
          wsHandler: WebSocketHandler,
          attributes: MutableMap<String, Any>
      ): Boolean {
        if (request is ServletServerHttpRequest) {
          attributes["IP_ADDRESS"] = request.remoteAddress
        }
        return true
      }

      override fun afterHandshake(
          request: ServerHttpRequest,
          response: ServerHttpResponse,
          wsHandler: WebSocketHandler,
          exception: Exception?
      ) {
        // do nothing...
      }
    }
  }

  private fun defaultHandshakeHandler(): DefaultHandshakeHandler {
    return object : DefaultHandshakeHandler() {
      override fun determineUser(
          request: ServerHttpRequest,
          wsHandler: WebSocketHandler,
          attributes: Map<String, Any>
      ): Principal {
        return request.principal ?: AnonymousAuthenticationToken(
            "WebsocketConfiguration",
            "anonymous",
            listOf(SimpleGrantedAuthority(ANONYMOUS.role))
        )
      }
    }
  }
}

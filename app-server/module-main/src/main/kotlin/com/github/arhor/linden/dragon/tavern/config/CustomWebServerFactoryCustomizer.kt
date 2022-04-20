package com.github.arhor.linden.dragon.tavern.config

import io.undertow.server.DefaultByteBufferPool
import io.undertow.servlet.api.DeploymentInfo
import io.undertow.websockets.jsr.WebSocketDeploymentInfo
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@Component
class CustomWebServerFactoryCustomizer : WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @Override
    override fun customize(factory: UndertowServletWebServerFactory) {
        factory.addDeploymentInfoCustomizers(::webSocketCustomizer)
    }

    private fun webSocketCustomizer(deployment: DeploymentInfo) {
        val byteBufferPool = DefaultByteBufferPool(IS_DIRECT_BUFFER, BYTE_BUFFER_SIZE)
        val webSocketDeploymentInfo = WebSocketDeploymentInfo().setBuffers(byteBufferPool)

        deployment.addServletContextAttribute(WEB_SOCKET_DEPLOYMENT_INFO, webSocketDeploymentInfo)
    }

    companion object {
        private const val IS_DIRECT_BUFFER = false
        private const val BYTE_BUFFER_SIZE = 1024
        private const val WEB_SOCKET_DEPLOYMENT_INFO = "io.undertow.websockets.jsr.WebSocketDeploymentInfo"
    }
}

package com.example.app.users.configuration

import com.nimbusds.jose.JWSAlgorithm
import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@ConfigurationProperties(prefix = "app.security")
open class AuthorizationServerConfigurationProperties(
    var client: String = "spa",
    var issuer: String = "https://example.com",
    var algorithm: Algorithm = Algorithm.RS256,
    val key: Key?
) {
    enum class Algorithm(val jwsAlgorithm: JWSAlgorithm) {
        RS256(JWSAlgorithm.RS256)
    }

    class Key(val public: RSAPublicKey, val private: RSAPrivateKey)
}

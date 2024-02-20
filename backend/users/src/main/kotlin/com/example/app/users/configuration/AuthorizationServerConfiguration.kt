package com.example.app.users.configuration

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
@EnableConfigurationProperties(AuthorizationServerConfigurationProperties::class)
@EnableWebSecurity
open class AuthorizationServerConfiguration(val properties: AuthorizationServerConfigurationProperties) {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Throws(java.lang.Exception::class)
    open fun authorizationServerSecurityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity)
        httpSecurity
            .getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
            .oidc(Customizer.withDefaults())
        httpSecurity
            .exceptionHandling { exceptions -> exceptions.defaultAuthenticationEntryPointFor(
                LoginUrlAuthenticationEntryPoint(properties.issuer + "/login"),
                MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            ) }
            .oauth2ResourceServer { resource -> resource.jwt(Customizer.withDefaults()) }
        return httpSecurity.build()
    }

    @Bean
    open fun registeredClientRepository(): RegisteredClientRepository {
        val oidcClient = RegisteredClient
            .withId(properties.client)
            .clientId(properties.client)
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(properties.issuer + "/login/oauth2/code")
            .postLogoutRedirectUri(properties.issuer)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .clientSettings(
                ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .requireProofKey(true)
                .build())
            .build()
        return InMemoryRegisteredClientRepository(oidcClient)
    }

    @Bean
    open fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder()
            .issuer(properties.issuer)
            .build() // todo (fromiva): subject to endpoints unification with /api/users/** pattern
    }

    @Bean
    open fun jwkSource(): JWKSource<SecurityContext> {
        val pair = getRsaKeyPair()
        val key = RSAKey.Builder(pair.public as RSAPublicKey)
            .privateKey(pair.private as RSAPrivateKey)
            .algorithm(properties.algorithm.jwsAlgorithm)
            .build()
        return ImmutableJWKSet(JWKSet(key))
    }

    @Bean
    open fun jwtDecoder(source: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(source)
    }

    private fun getRsaKeyPair(): KeyPair {
        val key = properties.key
        return if (key != null) KeyPair(key.public, key.private) else generateRsaKeyPair()
    }

    private fun generateRsaKeyPair(): KeyPair {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048)
        return generator.generateKeyPair()
    }
}

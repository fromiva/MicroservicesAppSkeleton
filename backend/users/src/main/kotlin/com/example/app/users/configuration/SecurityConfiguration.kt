package com.example.app.users.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableConfigurationProperties(AuthorizationServerConfigurationProperties::class)
@EnableWebSecurity
open class SecurityConfiguration {

    @Bean
    @Throws(java.lang.Exception::class)
    open fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .authorizeHttpRequests { authorize -> authorize.anyRequest().authenticated() }
            .formLogin(Customizer.withDefaults()) // todo (fromiva): subject to specification of the defaultSuccessUrl() according to future SPA needs
            .csrf { csrf -> csrf.disable() } // todo (fromiva): subject to remove according to gateway customisation
        return httpSecurity.build()
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}

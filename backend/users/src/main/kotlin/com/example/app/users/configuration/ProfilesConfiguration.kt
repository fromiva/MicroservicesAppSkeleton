package com.example.app.users.configuration

import com.example.app.users.User
import com.example.app.users.UserRepository
import com.example.app.users.UserRole
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

@Configuration
open class ProfilesConfiguration {

    @Bean
    @Profile("dev")
    open fun createStubUsers(repository: UserRepository): CommandLineRunner {
        return CommandLineRunner { args ->
            if (repository.count() < 1) {
                val admin = User("admin", "{noop}password", UserRole.ADMIN, true, false)
                val user = User("user", "{noop}password", UserRole.USER, true, false)
                val guest = User("guest", "{noop}password", UserRole.GUEST, true, false)
                repository.saveAll(setOf(admin, user, guest))
            }
        }
    }

    @Bean
    @Profile("dev")
    open fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { webSecurity ->
            webSecurity.ignoring().requestMatchers(
                OrRequestMatcher(
                    AntPathRequestMatcher("/h2-console/**"),
                    AntPathRequestMatcher("/favicon.ico"),
                    AntPathRequestMatcher("/error")
                )
            )
        }
    }
}

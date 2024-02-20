package com.example.app.users

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
@FunctionalInterface
open class UserToUserDetailsConverter: Converter<User, UserDetails> {
    override fun convert(source: User): UserDetails {
        return UserDetailsImpl(
            source.username,
            source.password,
            source.role,
            source.enabled,
            source.locked,
            source.expired
        )
    }
}

package com.example.app.users

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val repository: UserRepository,
    private val converter: UserToUserDetailsConverter
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return converter.convert(repository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException(username) })
    }
}

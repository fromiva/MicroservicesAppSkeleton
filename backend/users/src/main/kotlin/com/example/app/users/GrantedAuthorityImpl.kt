package com.example.app.users

import org.springframework.security.core.GrantedAuthority

data class GrantedAuthorityImpl(private val name: String): GrantedAuthority {
    override fun getAuthority(): String {
        return name
    }
}

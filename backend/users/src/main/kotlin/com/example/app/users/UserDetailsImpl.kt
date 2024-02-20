package com.example.app.users

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

data class UserDetailsImpl(
    private val username: String,
    private val password: String,
    private val role: UserRole,
    private val enabled: Boolean,
    private val locked: Boolean,
    private val expired: Instant?
): UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return role.authorities()
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return !locked
    }

    override fun isAccountNonExpired(): Boolean {
        return if (expired == null) true else !Instant.now().isBefore(expired)
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return username == (other as UserDetailsImpl).username
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }
}

package com.example.app.users

import org.springframework.security.core.GrantedAuthority

/** Represents a User security role with authorities. */
enum class UserRole {

    GUEST, USER, ADMIN;

    /** Returns the role name with ROLE_ prefix. */
    open fun role(): String {
        return "ROLE_$name"
    }

    /** Returns the set of GrantedAuthority for the current role. */
    open fun authorities(): Collection<GrantedAuthority> {
        return setOf(GrantedAuthorityImpl(name))
    }
}

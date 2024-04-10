package com.example.app.users

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.time.ZoneId
import java.util.*

/** Represents a user entity to store in the persistent storage. */
@Table(name = "USERS")
data class User (
    @Id
    var id: Long?,
    var username: String,
    @JsonIgnore var password: String,
    var role: UserRole,
    var enabled: Boolean,
    var locked: Boolean,
    var created: Instant,
    var updated: Instant?,
    var expired: Instant?,
    var namePrefix: String?,
    var firstName: String?,
    var middleName: String?,
    var lastName: String?,
    var nameSuffix: String?,
    var title: String?,
    var email: String?,
    var avatar: String?,
    var timezone: ZoneId?,
    var locale: Locale?) {

    /** Class constructor with required fields only, while all the nullable fields are null. */
    constructor(
        username: String,
        password: String,
        role: UserRole,
        enabled: Boolean,
        locked: Boolean
    ): this(
        null,
        username,
        password,
        role,
        enabled,
        locked,
        Instant.now(),
        null, null, null, null, null, null, null, null, null, null, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return this.id != null && this.id == (other as User).id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

package com.example.app.users

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
}

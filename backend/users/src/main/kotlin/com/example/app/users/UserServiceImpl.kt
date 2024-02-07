package com.example.app.users

import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val repository: UserRepository): UserService {

    override fun save(user: User): User {
        return repository.save(user)
    }

    override fun getById(id: Long): User {
        return repository.findById(id).orElseThrow()
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}

package com.example.app.users

interface UserService {

    fun save(user: User): User

    fun getById(id: Long): User

    fun deleteById(id: Long)
}

package com.example.app.users.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/users")
class UserController {

    @GetMapping
    fun stubGetUsers() :ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}
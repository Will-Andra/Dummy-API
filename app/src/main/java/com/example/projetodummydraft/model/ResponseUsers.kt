package com.example.projetodummydraft.model

data class ResponseUsers(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)
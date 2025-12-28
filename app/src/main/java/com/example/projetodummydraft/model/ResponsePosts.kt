package com.example.projetodummydraft.model

data class ResponsePosts(
    val limit: Int,
    val posts: List<Post>,
    val skip: Int,
    val total: Int
)
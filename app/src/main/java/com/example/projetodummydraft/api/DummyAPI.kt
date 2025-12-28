package com.example.projetodummydraft.api


import com.example.projetodummydraft.model.ResponsePosts
import com.example.projetodummydraft.model.ResponseProducts
import com.example.projetodummydraft.model.ResponseUsers
import com.example.projetodummydraft.model.RespostaCart
import retrofit2.Response
import retrofit2.http.GET

interface DummyAPI {

    //https://dummyjson.com/carts
    @GET("carts")  // https://dummyjson.com/carts
    suspend fun recuperaCarts(): Response<RespostaCart>

    @GET("products")  // https://dummyjson.com/products
    suspend fun recuperaProducts(): Response<ResponseProducts>

    @GET("users")  // https://dummyjson.com/users
    suspend fun recuperaUsers(): Response<ResponseUsers>

    @GET("posts")  //https://dummyjson.com/posts
    suspend fun recuperaPosts(): Response<ResponsePosts>
}




package com.example.projetodummydraft.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.java


object RetrofitService {

    const val BASE_URL = "https://dummyjson.com/" // https://dummyjson.com/carts

    const val API_KEY = ""

    private val okHttpClient = OkHttpClient.Builder() //config como as requisições HTTP devem funcionar
        .writeTimeout(10, TimeUnit.SECONDS) // Tempo limite para escrita de dados
        .readTimeout(20, TimeUnit.SECONDS)  // Tempo limite para leitura de dados
        // Não precisamos do interceptor por agora, pois não há chaves no header.
        /*.addInterceptor { chain ->
            chain.proceed(chain.request()) //esta parte está lá no AuthInterceptor()
        }*/
        .addInterceptor (AuthInterceptor())
        .build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
  /*  fun getCartAPI(): CartAPI {
        return retrofit.create(CartAPI::class.java)
    }*/

    val dummyAPI = retrofit.create(DummyAPI::class.java)
    //O Retrofit está criando uma implementação concreta da sua interface DummyAPI
    // (Retrofit cria em tempo de execução)

}
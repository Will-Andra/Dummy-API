package com.example.projetodummydraft.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(): Interceptor {

        //Classe para interceptar TODAS as requisições HTTP feitas pelo Retrofit antes de irem para a API.

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("Auth_Int", "Interceptor executado")

        //1)acessar a requisição e cria uma cópia mutável (vc nunca altera, vc cria outra)
        val construtorRequisição = chain.request().newBuilder()

        //2)alterar a URL ou Rota da Requisição
        val urlAtual = chain.request().url() //acessando a url atual feita com todos os dados que o Retrofit criou(BseURL, Endpoint, etc)
        val novaURL = urlAtual.newBuilder() //Cria uma URL Modificável podendo adiciona Query params, parte de url, deixar como esta.
        //novaURL.addQueryParameter("api_key",RetrofitService.API_KEY) //não solicitado pelo api um api_key
        //Se estivesse ativo, isso faria: https://api.exemplo.com/users?page=1&api_key=XYZ

        //3)Configurar a nova URL na requisição (substituindo)
        construtorRequisição.url(novaURL.build())

        return chain.proceed(construtorRequisição.build()) // Response: Proceed (prossiga com a execução)
    }
}
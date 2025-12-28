package com.example.projetodummydraft.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetodummydraft.R
import com.example.projetodummydraft.adapter.PostsAdapter
import com.example.projetodummydraft.api.RetrofitService
import com.example.projetodummydraft.databinding.ActivityPostsBinding
import com.example.projetodummydraft.model.Post
import com.example.projetodummydraft.model.ResponsePosts
//import com.google.mlkit.nl.translate.Translator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.Internal
import retrofit2.Response

class PostsActivity : AppCompatActivity() {


    //binding
    private val binding by lazy {
        ActivityPostsBinding.inflate(layoutInflater)
    }

    //Adapter
    private lateinit var adapterPost: PostsAdapter

    //Retrofit
    private val postAPI by lazy {
        RetrofitService.dummyAPI
    }

    //Job
    var postJob: Job? = null

    //private lateinit var translator: Translator

    private val TAG = "Info_Post"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Postagens"

        inicializar()
        recuperarPost()

    }

    private fun recuperarPost() {
        postJob = CoroutineScope(Dispatchers.IO).launch {
            var resposta: Response<ResponsePosts>
            try {
                resposta = postAPI.recuperaPosts()
            }catch (e: Exception){
                e.printStackTrace()
                exibirMensagem("Erro ao realizar a requisição, verifique o acesso a internet")
                return@launch
            }

            if (resposta != null){
                if (resposta.isSuccessful){
                    val postResposta = resposta.body()
                    if (postResposta!= null){
                        val listaPosts = postResposta.posts
                        withContext(Dispatchers.Main){
                            adapterPost.atualizaListPosts(listaPosts)
                            Log.i(TAG,"Dados Carregados com Sucesso: ${listaPosts.size}")
                        }
                    }
                }
            }else{
                val codigoErro = resposta.code
                Log.i(TAG,"Erro na resposta da API: ${codigoErro}")
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Erro ao carregar dados: $codigoErro",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun inicializar() {
        //Adapter
        adapterPost = PostsAdapter(){post ->
            val intent = Intent(this, DetalhesPosts::class.java)
            intent.putExtra("post",post)
            startActivity(intent)
        }

        //RecyclerView
        binding.recyclerPosts.adapter = adapterPost
        binding.recyclerPosts.layoutManager= LinearLayoutManager(this)
        binding.recyclerPosts.setHasFixedSize(true)
        binding.recyclerPosts.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL ))

    }


    fun exibirMensagem(msg: String){
        CoroutineScope(Dispatchers.Main).launch{
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        postJob?.cancel()
    }

    override fun onStart() {
        super.onStart()
        recuperarPost()
    }

/*    override fun onDestroy() {
        super.onDestroy()
        translator.close()
    }*/

}
package com.example.projetodummydraft.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetodummydraft.adapter.UsersAdapter
import com.example.projetodummydraft.api.RetrofitService
import com.example.projetodummydraft.databinding.ActivityUsersBinding
import com.example.projetodummydraft.model.ResponseUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class UsersActivity : AppCompatActivity() {

    //binding
    private val binding by lazy {
        ActivityUsersBinding.inflate(layoutInflater)
    }

    //Adapter
    private lateinit var usersAdapter: UsersAdapter

    //RetrofitService
    private val userAPI by lazy {
        RetrofitService.dummyAPI
    }

    //Job
    private var jobUser: Job? = null

    private val TAG = "Info_Produto"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Usuários"
        inicializar()
        recuperaUsers()
    }

    private fun recuperaUsers() {
        jobUser = CoroutineScope(Dispatchers.IO).launch {
            var resposta: Response<ResponseUsers>? = null

            try {
                resposta = userAPI.recuperaUsers()

            } catch(e: Exception){
                e.printStackTrace()
                exibirMensagem("Erro ao realizar a requisição, verifique o acesso a internet")
                return@launch //return rotulado. Encerra a coroutine
            }
            if (resposta!=null){
                if(resposta.isSuccessful){
                    val respostaUsers = resposta.body()

                    if (respostaUsers != null){
                        val listaUsers = respostaUsers.users
                        withContext(Dispatchers.Main){
                            usersAdapter.atualizarListaUser(listaUsers)
                            Log.i( TAG, "Dados Carregados com Sucesso: ${listaUsers.size} ")
                        }
                    }
                }
            }else{
                val codigoErro = resposta.code()
                Log.e(TAG, "Erro na resposta da API: $codigoErro")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@UsersActivity,
                        "Erro ao carregar dados: $codigoErro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun inicializar() {
        //Adapter - o que vai fazer qdo acionado (passando uma funçaõ de clique "cart ->", enviando um obj Cart
        usersAdapter = UsersAdapter(){ user ->

            val intent = Intent(this, DetalhesUsers::class.java)
            intent.putExtra("user",user)
            startActivity(intent)

        }
        //Recycler
        binding.recyclerUsers.adapter = usersAdapter
        binding.recyclerUsers.layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.setHasFixedSize(true)
        binding.recyclerUsers.addItemDecoration(
            DividerItemDecoration(binding.recyclerUsers.context,
                LinearLayoutManager.VERTICAL))
    }

    private fun exibirMensagem(mensagem: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                applicationContext,
                mensagem,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onStart() {
        super.onStart()
        recuperaUsers()
    }

    override fun onStop() {
        super.onStop()
        jobUser?.cancel()
    }
}
package com.example.projetodummydraft.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetodummydraft.adapter.CarAdapter
import com.example.projetodummydraft.api.RetrofitService
import com.example.projetodummydraft.databinding.ActivityCartBinding
import com.example.projetodummydraft.model.RespostaCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }

    private val TAG = "Info_Cart"
    private lateinit var cartAdapter: CarAdapter

    /*private val cartAPI by lazy {
        RetrofitService.getCartAPI() // Chama a nova função
    }*/

    private val cartAPI by lazy {
        RetrofitService.dummyAPI
    }

    var jobCart: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Carrinho"

        inicializacao()

        recuperarCarts()
    }

    private fun recuperarCarts() {

        jobCart = CoroutineScope(Dispatchers.IO).launch {
            var resposta: Response<RespostaCart>? = null

            try {
                resposta = cartAPI.recuperaCarts()

            } catch (e: Exception) {
                e.printStackTrace()
                exibirMensagem("Erro ao realizar a requisição, verifique o acesso a internet")
                return@launch

            }
            if (resposta != null) {
                if (resposta.isSuccessful) {

                    /* //Usand o ?.let (garante que não seja nullo)
                       val respostaCart: RespostaCart? = resposta.body()

                        withContext(Dispatchers.Main) {
                            respostaCart?.let {  // Se respostaCart não for nulo, pegue este objeto (it) e use-o para executar o código a seguir..
                                //Dentro do bloco let, você não precisa mais usar o ? ou o !! (double bang) ao acessar as propriedades. O Kotlin sabe que it (que é o respostaCart não-nulo) é seguro.
                                // Extrai a lista de Carts e a passa para o adaptador
                                cartAdapter.atualizarLista(it.carts)
                                Log.i(TAG, "Dados carregados com sucesso: ${it.carts.size} carrinhos.")
                            }
                        }*/

                    val respostaCart = resposta.body()

                    if (respostaCart != null) {
                        val listaCarts = respostaCart.carts
                        withContext(Dispatchers.Main) {
                            cartAdapter.atualizarLista(listaCarts)
                            Log.i(
                                TAG,
                                "Dados carregados com sucesso: ${listaCarts.size} carrinhos."
                            )
                        }
                    }
                } else {

                    val codigoErro = resposta.code()
                    Log.e(TAG, "Erro na resposta da API: $codigoErro")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@CartActivity,
                            "Erro ao carregar dados: $codigoErro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun exibirMensagem(mensagem: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                applicationContext,
                mensagem,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun inicializacao() {
        //Adapter - o que vai fazer qdo acionado (passando uma funçaõ de clique "cart ->", enviando um obj Cart
        cartAdapter = CarAdapter() { cart ->
            val intent = Intent(this, DetalhesCart::class.java)
            intent.putExtra("cart", cart)
            startActivity(intent)
        }

        //Recycler
        binding.recyclerCart.adapter = cartAdapter
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerCart.setHasFixedSize(true)
        binding.recyclerCart.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerCart.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }


    override fun onStart() {
        super.onStart()
        recuperarCarts()
    }

    override fun onStop() {
        super.onStop()
        jobCart?.cancel()
    }

}
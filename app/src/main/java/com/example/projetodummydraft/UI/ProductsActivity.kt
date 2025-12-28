package com.example.projetodummydraft.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetodummydraft.R
import com.example.projetodummydraft.adapter.ProductsAdapter
import com.example.projetodummydraft.api.RetrofitService
import com.example.projetodummydraft.databinding.ActivityProductsBinding
import com.example.projetodummydraft.model.ResponseProducts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProductsActivity : AppCompatActivity() {


    //Binding by lazy
    private val binding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }
    //Outra forma (Lateinit)
    //private lateinit var binding: ActivityProductsBinding

    //Adapter (Lateinit)
    private lateinit var productsAdapter: ProductsAdapter

    //RetrofitService (By lazy)
    private val prodAPI by lazy {
        RetrofitService.dummyAPI
    }

    //Job
    var jobProduct: Job? = null

    private val TAG = "Info_Produto"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Produtos"
        inicializar()
        recuperaProdutos()

    }

    private fun recuperaProdutos() {
        jobProduct = CoroutineScope(Dispatchers.IO).launch {
            var resposta: Response<ResponseProducts>? = null

            try {
                resposta = prodAPI.recuperaProducts()

            } catch(e: Exception){
                e.printStackTrace()
                exibirMensagem("Erro ao realizar a requisição, verifique o acesso a internet")
                return@launch //return rotulado. Encerra a coroutine
            }
            if (resposta!=null){
                if(resposta.isSuccessful){
                    val respostaProduct = resposta.body()

                    if (respostaProduct != null){
                        val listaProducts = respostaProduct.products
                        withContext(Dispatchers.Main){
                            productsAdapter.atualizaListProducts(listaProducts)
                            Log.i( TAG, "Dados Carregados com Sucesso: ${listaProducts.size} ")
                        }
                    }
                }
            }else{
                val codigoErro = resposta.code()
                Log.e(TAG, "Erro na resposta da API: $codigoErro")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ProductsActivity,
                        "Erro ao carregar dados: $codigoErro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun inicializar() {
        //Adapter - o que vai fazer qdo acionado (passando uma funçaõ de clique "cart ->", enviando um obj Cart
        productsAdapter = ProductsAdapter(){ product ->

            val intent = Intent(this, DetalhesProduct::class.java)
            intent.putExtra("product",product)
            startActivity(intent)
        }
        //Recycler
        binding.recyclerProduct.adapter = productsAdapter
        binding.recyclerProduct.layoutManager = LinearLayoutManager(this)
        binding.recyclerProduct.setHasFixedSize(true)
        binding.recyclerProduct.addItemDecoration(
            DividerItemDecoration(binding.recyclerProduct.context,
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
        recuperaProdutos()
    }

    override fun onStop() {
        super.onStop()
        jobProduct?.cancel()
    }

}
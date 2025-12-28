package com.example.projetodummydraft

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.projetodummydraft.UI.CartActivity
import com.example.projetodummydraft.UI.PostsActivity
import com.example.projetodummydraft.UI.ProductsActivity
import com.example.projetodummydraft.UI.UsersActivity
import com.example.projetodummydraft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Loja Virtual"

        binding.btnCarrinho.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity( intent )
        }

        binding.btnProduto.setOnClickListener {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity( intent )
        }

        binding.btnUser.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            startActivity( intent )
        }

        binding.btnPost.setOnClickListener {
            val intent = Intent(this, PostsActivity::class.java)
            startActivity( intent)
        }



    }
}
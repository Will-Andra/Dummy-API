package com.example.projetodummydraft.UI

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.projetodummydraft.R
import com.example.projetodummydraft.databinding.ActivityDetalhesProductBinding
import com.example.projetodummydraft.databinding.ActivityDetalhesUsersBinding
import com.example.projetodummydraft.model.Product
import com.example.projetodummydraft.model.User
import com.example.projetodummydraft.util.CurrencyFormatter

class DetalhesUsers : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesUsersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //sdk Version e recebimento do obj Produto que foi passado
        val userRecebido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("user", User::class.java)
        } else {
            intent.getParcelableExtra("user")
        }

        if (userRecebido != null) {
            supportActionBar?.title = "Detalhes do Usuario" //#${userRecebido.id}"

            var descricaoUser = "${userRecebido.firstName} ${userRecebido.maidenName} ${userRecebido.lastName}\n\n"
            descricaoUser += "${userRecebido.email} \n"
            descricaoUser += "Idade: ${userRecebido.age} \n"
            descricaoUser += "Telefone: ${userRecebido.phone} \n"
            descricaoUser += "Cidade: ${userRecebido.address.city} \n"
            descricaoUser += "País: ${userRecebido.address.country} \n\n\n"

            descricaoUser += "Informações Adicionai: \n\n"
            descricaoUser += "Universidade: ${userRecebido.university} \n"
            descricaoUser += "Company: ${userRecebido.company.name} \n"
            descricaoUser += "Cargo: ${userRecebido.company.title} \n"





            binding.txtDescricaoUsers.text = descricaoUser

            Glide.with(binding.imgUsers.context)
                .load(userRecebido.image)
                .placeholder(R.drawable.users_imagem) // Opcional: Imagem enquanto carrega
                .error(R.drawable.users_imagem)       // Opcional: Imagem se der erro
                .into(binding.imgUsers)
        }
    }
}

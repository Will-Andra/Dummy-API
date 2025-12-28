package com.example.projetodummydraft.UI

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.projetodummydraft.R
import com.example.projetodummydraft.databinding.ActivityDetalhesProductBinding
import com.example.projetodummydraft.model.Product
import com.example.projetodummydraft.util.CurrencyFormatter
import java.text.NumberFormat
import java.util.Locale

class DetalhesProduct : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProductBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //sdk Version e recebimento do obj Produto que foi passado
        val prodRecebido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("product", Product::class.java)
        } else {
            intent.getParcelableExtra("product")
        }

        val formatterPrice = CurrencyFormatter()
        if (prodRecebido != null) {
            supportActionBar?.title = "Detalhes do Produto #${prodRecebido.id}"
            var descricaoProd = "${prodRecebido.title} \n\n"
            descricaoProd += "${prodRecebido.description} \n\n"
            descricaoProd += "Price: ${formatterPrice.formatToBRL(prodRecebido.price) }"

            binding.txtDescricaoProducts.text = descricaoProd

            Glide.with(binding.imgProducts.context)
                .load(prodRecebido.thumbnail)
                .placeholder(R.drawable.product_imagem) // Opcional: Imagem enquanto carrega
                .error(R.drawable.product_imagem)       // Opcional: Imagem se der erro
                .into(binding.imgProducts)
        }
    }

    /*fun Double.toBRL():String{
        val formatoBR = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatoBR.format(this)
    }*/

}

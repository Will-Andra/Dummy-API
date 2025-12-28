package com.example.projetodummydraft.UI

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetodummydraft.databinding.ActivityDetalhesCartBinding
import com.example.projetodummydraft.model.Cart
import com.example.projetodummydraft.util.CurrencyFormatter
import java.text.NumberFormat
import java.util.Locale

class DetalhesCart : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🚨 Certifique-se de configurar o binding corretamente inicializando-o
        binding = ActivityDetalhesCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartRecebido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("cart", Cart::class.java)
            //Recupera do Intent um objeto Cart que foi enviado como Parcelable.
            //Cart = Informa ao Android qual tipo de objeto você espera receber
        } else {
            intent.getParcelableExtra<Cart>("cart")
        }

        if (cartRecebido != null) {
            // 1. Formata a string de detalhes
            val detalhesFormatados = formatarListaProdutos(cartRecebido)
            //Antes de jogar na variável lança os dados recebidos em uma função para formatar

            // 2. Popula o TextView com a string formatada
            // (Substitua 'txtDetalhesProdutos' pelo ID do seu TextView de destino)
            binding.txtDetalhesProdutos.text = detalhesFormatados

            // Opcional: Título da Activity
            supportActionBar?.title = "Detalhes do Carrinho #${cartRecebido.id}"
        }
    }

    private fun formatarListaProdutos(cart: Cart): String {
        // 1. Inicia a string que será preenchida
        var detalhes = "### Detalhes dos Produtos (${cart.products.size} itens) ###\n\n"

        // 2. Itera sobre a lista de produtos usando o 'for' clássico
        val formatterPrice = CurrencyFormatter()
        for (produto in cart.products) {

            val contador = cart.products.indexOf(produto) + 1

            // Concatenação de Strings usando o operador '+'
            detalhes += "#$contador - ID: " + produto.id + "\n"
            detalhes += " - Produto: " + produto.title + "\n"
            detalhes += " - Qtd: " + produto.quantity + "\n"
            // Uso direto de String.format() para formatação simples de casas decimais
            //detalhes += " - Preço/Unid: R$ " + String.format("%.2f", produto.price)
            //detalhes += " - Preço/Unid: ${produto.price.toBRL()}"
            detalhes += " - Preço/Unid: ${formatterPrice.formatToBRL( produto.price)}"
            detalhes += "\n\n" // Adiciona a quebra de linha para o próximo item
        }

        // 3. Adiciona o total ao final
        detalhes += "TOTAL FINAL: ${formatterPrice.formatToBRL(cart.total)}"
        //detalhes += "\nTOTAL FINAL: R$ " + String.format("%.2f", cart.total)
        return detalhes
    }

    /*private fun formatarListaProdutosSENIOR(cart: Cart): String {
        // StringBuilder é mais eficiente para construir strings em loop
        val builder = StringBuilder()

        // Título inicial
        builder.append("### Detalhes dos Produtos (${cart.products.size} itens) ###\n\n")

        // Itera sobre a lista de produtos
        cart.products.forEach { produto ->
            builder.append("ID: ${produto.id}")
            builder.append(" - Produto: ${produto.title}")
            builder.append(" - Qtd: ${produto.quantity}")
            builder.append(" - Preço/Unid: R$ ${String.format("%.2f", produto.price)}")
            builder.append("\n") // Quebra de linha para o próximo item
        }

        // Adiciona o total ao final
        builder.append("\n---")
        builder.append("\nTOTAL DO CARRINHO: R$ ${String.format("%.2f", cart.total)}")

        return builder.toString()
    }*/
}
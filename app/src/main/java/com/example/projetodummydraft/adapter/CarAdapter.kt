package com.example.projetodummydraft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetodummydraft.R
import com.example.projetodummydraft.databinding.ItemCartBinding
import com.example.projetodummydraft.model.Cart
import com.example.projetodummydraft.util.CurrencyFormatter
import java.text.NumberFormat
import java.util.Locale

class CarAdapter(
    val onClick: (Cart) -> Unit
) : RecyclerView.Adapter<CarAdapter.CartViewHolder>() {

    private var listaCart = mutableListOf<Cart>()

    fun atualizarLista(lista: List<Cart>) {
        listaCart.clear()
        listaCart.addAll(lista)
        notifyDataSetChanged()
    }

    //Limpa a lista (usado no onStart para reiniciar)
    /*fun limparLista() {
        listaCart.clear()
        notifyDataSetChanged()
    }*/
    private val formatterPrice = CurrencyFormatter()
    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemCart: Cart) {

            binding.txtId.text = "ID: ${itemCart.id}"
            binding.txtTitle.text = "Total Produtos: ${itemCart.totalProducts}"
            binding.txtCartPrice.text = "Total: ${formatterPrice.formatToBRL(itemCart.total)}"
            Glide.with(binding.imgCart.context) // O 'with' define o contexto (a "vida" do carregamento)
                .load(R.drawable.cart_imagem)                 // O 'load' define a fonte (a URL)
                .placeholder(R.drawable.cart_imagem) // Opcional: Imagem enquanto carrega
                .error(R.drawable.cart_imagem)       // Opcional: Imagem se der erro
                .into(binding.imgCart)

            binding.consLay.setOnClickListener {
                onClick(itemCart) // executa a func lambda recebida com o obj Cart recebido (fução que vem com um parâmetro dizendo o que fazer, nesse caso chamar outra activity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CartViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemViewCart = ItemCartBinding.inflate(layoutInflater, parent, false)

        return CartViewHolder(itemViewCart)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        var itemCart = listaCart[position]
        holder.bind(itemCart)
    }

    override fun getItemCount(): Int {
        return listaCart.size

    }

}
package com.example.projetodummydraft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetodummydraft.R
import com.example.projetodummydraft.databinding.ItemProductsBinding
import com.example.projetodummydraft.model.Product
import com.example.projetodummydraft.util.CurrencyFormatter
import java.text.NumberFormat
import java.util.Locale

class ProductsAdapter(private val onClick: (Product) -> Unit): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    //Lista
    private var listaProdutos = mutableListOf<Product>()

    //fun Atualizar Lista
    fun atualizaListProducts(lista: List<Product>){
        listaProdutos.clear()
        listaProdutos.addAll(lista)
        notifyDataSetChanged()
    }

    private val formatterPrice = CurrencyFormatter()
    inner class ProductsViewHolder(private val binding: ItemProductsBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind (product: Product){
            binding.txtIdProducts.text = "ID: ${product.id}"
            binding.txtTitleProducts.text = product.title
            binding.txtPriceProducts.text = "${formatterPrice.formatToBRL(product.price)}"

            Glide.with(binding.imgProducts.context)
                .load(product.thumbnail)
                .placeholder(R.drawable.product_imagem) // Opcional: Imagem enquanto carrega
                .error(R.drawable.product_imagem)       // Opcional: Imagem se der erro
                .into(binding.imgProducts)


            binding.ConsLayProducts.setOnClickListener {
                onClick(product)
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemViewProducts = ItemProductsBinding.inflate(layoutInflater, parent, false)

        return ProductsViewHolder(itemViewProducts)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val itemProduct = listaProdutos[position]
        holder.bind(itemProduct)
    }

    override fun getItemCount(): Int {
        return listaProdutos.size
    }
    /*fun Double.toBRL(): String {
        //função de extensão (extension function)
        //adiciona um novo comportamento do tipo Double, sem modificar a classe Double original
        //o nome da função é "toBRL" e ao final retorna uma String
        //Resumo: “Crie uma função chamada toBRL que pertence ao Double e retorna uma String”
        val formatoBR = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatoBR.format(this)
    }*/

}
package com.example.projetodummydraft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetodummydraft.databinding.ItemUsersBinding
import com.example.projetodummydraft.model.User



class UsersAdapter(private val onClick: (User) -> Unit): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    //Lista
    private val listaUser = mutableListOf<User>()

    //Atualizar lista
    fun atualizarListaUser(lista: List<User>){
        listaUser.clear()
        listaUser.addAll(lista)
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(private val binding: ItemUsersBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User){
            binding.txtnome.text = user.firstName + " " + user.lastName
            binding.txtemail.text = user.email

            Glide.with(binding.imgUsers.context)
                .load(user.image)
                .placeholder(com.example.projetodummydraft.R.drawable.users_imagem)
                .error(com.example.projetodummydraft.R.drawable.users_imagem)
                .into(binding.imgUsers)

            binding.consLayUser.setOnClickListener {
                onClick(user)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itenViewUser = ItemUsersBinding.inflate(layoutInflater, parent,false)
        return UsersViewHolder(itenViewUser)
    }

    override fun onBindViewHolder(
        holder: UsersViewHolder,
        position: Int
    ) {
        val userContact = listaUser[position]
        holder.bind(userContact)
    }

    override fun getItemCount(): Int {
        return  listaUser.size
    }

}
package com.example.projetodummydraft.adapter

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetodummydraft.R
import com.example.projetodummydraft.databinding.ItemPostsBinding
import com.example.projetodummydraft.model.Post
import java.text.NumberFormat
import java.util.Locale

class PostsAdapter(private val onClick:(Post)-> Unit): RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    //Lista
    private val listaPosts = mutableListOf<Post>()

    //Recupera Lista
    fun atualizaListPosts(lista: List<Post>){
        listaPosts.clear()
        listaPosts.addAll(lista)
        notifyDataSetChanged()
    }

    inner class PostsViewHolder(private val binding: ItemPostsBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            val numformatBR = NumberFormat.getNumberInstance(Locale("pt","BR"))

            binding.txtPostsID.text = "Post ID: ${post.id}"
            binding.txtPostsTitle.text = "${post.title}"
            //binding.txtPostsViews.text = "Views: ${post.views}"
            //binding.txtPostsViews.text = "Views: " + String.format("%,d",post.views)
            binding.txtPostsViews.text = "Views: ${numformatBR.format(post.views)}"

            Glide.with(binding.imgPost.context)
                .load(R.drawable.posts_imagem)
                .error(R.drawable.posts_imagem)
                .placeholder(R.drawable.posts_imagem)
                .into(binding.imgPost)

            binding.ConstLayPosts.setOnClickListener {
                onClick(post)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemPostsBinding = ItemPostsBinding.inflate(layoutInflater,parent,false)
        return PostsViewHolder(itemPostsBinding)
    }

    override fun onBindViewHolder(holder: PostsAdapter.PostsViewHolder, position: Int) {
        val listPostagem = listaPosts[position]
        holder.bind(listPostagem)
    }

    override fun getItemCount(): Int {
        return listaPosts.size
    }


}
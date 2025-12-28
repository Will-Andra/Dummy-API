package com.example.projetodummydraft.UI

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.projetodummydraft.R
import com.example.projetodummydraft.databinding.ActivityDetalhesPostsBinding
import com.example.projetodummydraft.model.Post
//import com.example.projetodummydraft.util.Translate
//import com.google.mlkit.nl.translate.Translator
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class DetalhesPosts : AppCompatActivity() {

    private val binding by lazy{
        ActivityDetalhesPostsBinding.inflate(layoutInflater)
    }

    //private val translate = Translate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Detalhes do Post"

        //sdk Version e recebimento do obj Post que foi passado
        val postRecebido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("post", Post::class.java)
        }else{
            intent.getParcelableExtra("post")
        }

        if(postRecebido != null){
            supportActionBar?.title = "Detalhes do Post #${postRecebido.id}"

            var descicaoPost = "Post: ${postRecebido.title} \n\n"
             descicaoPost += postRecebido.body

            var numbFormat = NumberFormat.getNumberInstance(Locale("pt","BR"))

            binding.txtDescricaoPosts.text = descicaoPost

            binding.textViewPost.text = "Views: ${numbFormat.format(postRecebido.views)}"
            binding.txtLikedPost.text = "Likes: ${numbFormat.format(postRecebido.reactions.likes)}"

        }

        //Tentei usar o translator mas não foi possível, mas deixei o código aqui feito
        // Erro: APK app-debug.apk is not compatible with 16 KB devices. Some libraries have LOAD segments not aligned at 16 KB boundaries

        /*postRecebido?.let { post -> //let é uma função de escopo do Kotlin Ela: recebe o objeto (postRecebido), executa o bloco, passa o objeto para dentro do bloco
        // ?.let { } = if (objeto != null) { use o objeto }
            supportActionBar?.title = "Detalhes do Post #${post.id}"

            val numberFormat = NumberFormat.getNumberInstance(Locale("pt", "BR"))

            // ✅ MOSTRA O TEXTO ORIGINAL IMEDIATAMENTE

            var txtDescricao = "Post: ${post.title} \n\n"
            txtDescricao += post.body

            binding.txtDescricaoPosts.text = txtDescricao

            binding.textViewPost.text =
                "Views: ${numberFormat.format(post.views)}"

            binding.txtLikedPost.text =
                "Likes: ${numberFormat.format(post.reactions.likes)}"

            // 🔹 depois tenta traduzir
            translateTXT(post)
        }
*/
    }

   /* private fun translateTXT(post: Post) {

        lifecycleScope.launch {
            val translated = translate.translate(post.body)
            binding.txtDescricaoPosts.text = translated
        }

    }*/


}
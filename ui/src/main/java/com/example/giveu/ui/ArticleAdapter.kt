package com.example.giveu.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giveu.ui.databinding.ItemArticleBinding
import com.example.giveu.domain.model.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.Holder>() {
    private var items: List<Article> = emptyList()

    class Holder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val article = items[position]
        holder.binding.articleTitle.text = article.title
        holder.binding.articleDescription.text = article.description
        holder.binding.articlePhone.text =
            "Telefono: ${article.phoneNumber.ifBlank { "non disponibile" }}"
        holder.binding.articleLocation.text =
            "Luogo: ${article.location.ifBlank { "non disponibile" }}"
        Glide.with(holder.itemView).load(article.imageUrl).into(holder.binding.articleImage)
        holder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    ArticleDetailActivity::class.java
                ).putExtra("article", article)
            )
        }
    }

    fun submit(list: List<Article>) {
        items = list.distinctBy { "${it.id}-${it.title}" }; notifyDataSetChanged()
    }
}

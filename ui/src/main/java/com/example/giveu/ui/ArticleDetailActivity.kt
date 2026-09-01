package com.example.giveu.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.giveu.domain.model.Article
import com.example.giveu.ui.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityArticleDetailBinding.inflate(layoutInflater); setContentView(b.root)
        val article = intent.getSerializableExtra("article") as? Article ?: run { finish(); return }
        b.detailTitle.text = article.title; b.detailDescription.text = article.description
        b.detailPhone.text = "Chiama: ${article.phoneNumber.ifBlank { "numero non disponibile" }}"
        b.detailLocation.text = "Luogo: ${article.location.ifBlank { "non disponibile" }}"
        Glide.with(this).load(article.imageUrl).into(b.detailImage)
        b.backButton.setOnClickListener { finish() }
        b.detailPhone.setOnClickListener {
            if (article.phoneNumber.isNotBlank()) {
                val number = article.phoneNumber.filter { it.isDigit() || it == '+' }
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
            }
        }
        b.detailLocation.setOnClickListener {
            if (article.location.isNotBlank()) try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=${Uri.encode(article.location)}")
                    )
                )
            } catch (_: Exception) {
                Toast.makeText(this, "Nessuna app Maps disponibile", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

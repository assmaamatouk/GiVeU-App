package com.example.giveu.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.giveu.data.work.ArticleRefreshWorker
import com.example.giveu.ui.databinding.ActivityHomeBinding
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityHomeBinding.inflate(layoutInflater); setContentView(b.root)
        b.addButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddArticleActivity::class.java
                )
            )
        }
        b.categoryComputer.setOnClickListener { open("laptops", "smartphones") }
        b.categoryDress.setOnClickListener { open("mens-shirts", "womens-dresses") }
        b.categoryHome.setOnClickListener { open("home-decoration", "furniture") }
        b.categoryMakeup.setOnClickListener { open("beauty", "skincare") }
        b.categoryCar.setOnClickListener { open("automotive", "motorcycle") }
        b.categorySport.setOnClickListener { open("sports-accessories") }
        b.categoryPet.setOnClickListener { open("groceries") }
        b.categoryBook.setOnClickListener { open("books", books = true) }
        val work = PeriodicWorkRequestBuilder<ArticleRefreshWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("refresh_articles", ExistingPeriodicWorkPolicy.KEEP, work)
    }

    private fun open(vararg categories: String, books: Boolean = false) = startActivity(
        Intent(this, ArticlesActivity::class.java).putExtra("categories", categories)
            .putExtra("books", books)
    )
}

package com.example.giveu.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giveu.ui.databinding.ActivityArticoliBinding
import kotlinx.coroutines.launch

class ArticlesActivity : AppCompatActivity() {
    private val viewModel: ArticleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityArticoliBinding.inflate(layoutInflater); setContentView(b.root)
        val adapter = ArticleAdapter(); b.recyclerView.layoutManager =
            LinearLayoutManager(this); b.recyclerView.adapter = adapter
        b.backButton.setOnClickListener { finish() }
        val categories = intent.getStringArrayExtra("categories")?.toList() ?: return
        val books = intent.getBooleanExtra("books", false)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect { adapter.submit(it) }
            }
        }

        viewModel.loadArticles(categories = categories, books = books)
    }
}

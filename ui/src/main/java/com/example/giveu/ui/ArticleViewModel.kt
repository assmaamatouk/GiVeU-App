package com.example.giveu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giveu.domain.di.UseCaseProvider
import com.example.giveu.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val _localArticles = MutableStateFlow<List<Article>>(listOf())
    private val _remoteArticles = MutableStateFlow<List<Article>>(listOf())

    private val _articles = MutableStateFlow<List<Article>>(listOf())
    val articles: StateFlow<List<Article>> = _articles

    private val _showLoader = MutableStateFlow(false)
    val showLoader: StateFlow<Boolean> = _showLoader

    fun loadArticles(categories: List<String>, books: Boolean) {
        //Osserva in continuo gli articoli salvati in locale (Room)
        viewModelScope.launch {
            UseCaseProvider.getLocalArticlesUseCase.invoke(categories = categories).collect { local ->
                _localArticles.emit(local)
                _articles.emit(_localArticles.value + _remoteArticles.value)
            }
        }

        //Recupera gli articoli
        viewModelScope.launch {
            _showLoader.emit(true)
            val remote = try {
                UseCaseProvider.fetchRemoteArticlesUseCase.invoke(categories = categories, books = books)
            } catch (_: Exception) {
                emptyList()
            }
            _remoteArticles.emit(remote)
            _articles.emit(_localArticles.value + _remoteArticles.value)
            _showLoader.emit(false)
        }
    }

    fun save(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        UseCaseProvider.saveArticleUseCase.invoke(article = article)
    }
}

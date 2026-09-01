package com.example.giveu.domain.repository

import com.example.giveu.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun localArticles(categories: List<String>): Flow<List<Article>>
    suspend fun save(article: Article)
    suspend fun remoteArticles(categories: List<String>): List<Article>
    suspend fun books(): List<Article>
}

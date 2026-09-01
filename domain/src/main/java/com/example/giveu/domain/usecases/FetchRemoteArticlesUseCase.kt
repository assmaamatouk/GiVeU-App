package com.example.giveu.domain.usecases

import com.example.giveu.domain.model.Article
import com.example.giveu.domain.repository.ArticleRepository

interface FetchRemoteArticlesUseCase {
    suspend fun invoke(categories: List<String>, books: Boolean): List<Article>
}

class FetchRemoteArticlesUseCaseImpl(
    private val articleRepository: ArticleRepository
) : FetchRemoteArticlesUseCase {
    override suspend fun invoke(categories: List<String>, books: Boolean): List<Article> {
        return if (books) {
            articleRepository.books()
        } else {
            articleRepository.remoteArticles(categories = categories)
        }
    }
}

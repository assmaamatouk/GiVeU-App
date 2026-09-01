package com.example.giveu.domain.di

import com.example.giveu.domain.repository.ArticleRepository


interface RepositoryProvider {
    val articleRepository: ArticleRepository
}

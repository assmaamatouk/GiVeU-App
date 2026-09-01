package com.example.giveu.data.di

import android.content.Context
import com.example.giveu.data.repository.ArticleRepositoryImpl
import com.example.giveu.domain.di.RepositoryProvider
import com.example.giveu.domain.repository.ArticleRepository

class RepositoryProviderImpl(context: Context) : RepositoryProvider {
    override val articleRepository: ArticleRepository =
        ArticleRepositoryImpl(context.applicationContext)
}

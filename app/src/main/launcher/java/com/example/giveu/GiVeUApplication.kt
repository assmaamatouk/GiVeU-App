package com.example.giveu

import android.app.Application
import com.example.giveu.data.di.RepositoryProviderImpl
import com.example.giveu.domain.di.UseCaseProvider

class GiVeUApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UseCaseProvider.setup(
            repositoryProvider = RepositoryProviderImpl(context = this.applicationContext)
        )
    }
}

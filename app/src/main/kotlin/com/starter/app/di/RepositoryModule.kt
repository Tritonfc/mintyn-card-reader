package com.starter.app.di

import com.starter.app.repository.CardRepository
import com.starter.app.repository.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
 abstract class RepositoryModule {

    @Binds
    abstract fun bindCardRepository(impl: CardRepositoryImpl): CardRepository
}
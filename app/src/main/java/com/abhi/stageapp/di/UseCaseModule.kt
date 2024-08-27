package com.abhi.stageapp.di

import com.abhi.stageapp.repository.MainRepository
import com.abhi.stageapp.usecase.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideMainUseCase(repository: MainRepository): MainUseCase {
        return MainUseCase(repository)
    }

}
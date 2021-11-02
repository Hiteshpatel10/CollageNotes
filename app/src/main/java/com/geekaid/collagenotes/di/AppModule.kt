package com.geekaid.collagenotes.di

import com.geekaid.collagenotes.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideRepository() : Repository{
        return Repository()
    }
}
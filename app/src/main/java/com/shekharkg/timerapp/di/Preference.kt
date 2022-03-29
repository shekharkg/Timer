package com.shekharkg.timerapp.di

import android.content.Context
import com.shekharkg.timerapp.data.Preference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Preference {

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context) = Preference.create(context)

}
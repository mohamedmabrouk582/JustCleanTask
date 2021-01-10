package com.example.justcleantask.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.PostsDao
import com.example.data.db.PostsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun getDao(@ApplicationContext context: Context) : PostsDao =
        Room.databaseBuilder(context,PostsDb::class.java,"PostsDb").fallbackToDestructiveMigration()
            .build().getDao()
}
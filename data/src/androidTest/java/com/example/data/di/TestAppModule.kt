package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.PostsDao
import com.example.data.db.PostsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun getDB(@ApplicationContext context: Context) : PostsDb =
        Room.inMemoryDatabaseBuilder(context,PostsDb::class.java).allowMainThreadQueries()
            .build()
}
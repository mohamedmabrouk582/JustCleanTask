package com.example.justcleantask.di

import com.example.data.repository.comment.CommentRepository
import com.example.data.repository.comment.CommentsDefaultRepository
import com.example.data.repository.fav.FavDefaultRepository
import com.example.data.repository.fav.FavRepository
import com.example.data.repository.posts.PostRepository
import com.example.data.repository.posts.PostsDefaultRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun getRepository(repository: PostRepository) : PostsDefaultRepository

    @Binds
    @Singleton
    abstract fun getFavRepository(repository: FavRepository) : FavDefaultRepository

    @Binds
    @Singleton
    abstract fun getCommentsRepository(repository: CommentRepository) : CommentsDefaultRepository
}
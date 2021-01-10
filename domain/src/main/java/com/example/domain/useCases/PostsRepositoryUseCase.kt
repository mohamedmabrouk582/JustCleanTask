package com.example.domain.useCases

import com.example.domain.models.Post
import com.example.domain.repository.PostsRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

class PostsRepositoryUseCase(private val repository:PostsRepository) {
    suspend fun requestPosts() : Flow<Result<ArrayList<Post>>> =
        repository.requestAllPosts()

    suspend fun savePosts(data:ArrayList<Post>) =
        repository.savePosts(data)
}
package com.example.domain.repository

import com.example.domain.models.Post
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
  suspend fun requestAllPosts() : Flow<Result<ArrayList<Post>>>
  suspend fun savePosts(posts:ArrayList<Post>)
}
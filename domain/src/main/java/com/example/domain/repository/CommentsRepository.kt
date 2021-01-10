package com.example.domain.repository

import com.example.domain.models.Comment
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    suspend fun requestComments(postId:Int) : Flow<Result<ArrayList<Comment>>>
    suspend fun saveComments(comments:ArrayList<Comment>)
}
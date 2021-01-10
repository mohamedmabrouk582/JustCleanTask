package com.example.domain.useCases

import com.example.domain.models.Comment
import com.example.domain.repository.CommentsRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

class CommentsRepositoryUseCase(private val repository:CommentsRepository) {
    suspend operator fun invoke(postId:Int) : Flow<Result<ArrayList<Comment>>> =
        repository.requestComments(postId)

    suspend fun savedComments(data:ArrayList<Comment>) =
        repository.saveComments(data)
}
package com.example.domain.useCases

import com.example.domain.TestUtils
import com.example.domain.models.Comment
import com.example.domain.repository.CommentsRepository
import com.example.domain.utils.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CommentsRepositoryUseCaseTest{
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()
    private val repository= object : CommentsRepository {
        override suspend fun requestComments(postId: Int): Flow<Result<ArrayList<Comment>>> {
            return TestUtils.getComments()
        }

        override suspend fun saveComments(comments: ArrayList<Comment>) {
            TestUtils.comments.addAll(comments)
        }

    }

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `RequestComments(PostId)  return List Of Comments`() = testScope.runBlockingTest {
        val toList = repository.requestComments(1).first()
        assertThat(toList).isEqualTo(Result.OnSuccess(TestUtils.comments))
    }

    @Test
    fun `SavedPosts(postsList) saved Success`()=testScope.runBlockingTest {
        repository.saveComments(arrayListOf(Comment(4,1), Comment(5,1)))
        assertThat(TestUtils.comments).containsAnyIn(arrayListOf(Comment(4,1), Comment(5,1)))
    }
}
package com.example.domain.useCases

import com.example.domain.TestUtils
import com.example.domain.models.Post
import com.example.domain.repository.PostsRepository
import com.example.domain.utils.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsRepositoryUseCaseTest {
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()
    private val repository= object : PostsRepository{
        override suspend fun requestAllPosts(): Flow<Result<ArrayList<Post>>> {
            return TestUtils.getPosts()
        }

        override suspend fun savePosts(posts: ArrayList<Post>) {
           TestUtils.posts.addAll(posts)
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
    fun `RequestPosts() return List Of Posts`()= testScope.runBlockingTest {
          val first = repository.requestAllPosts().first()
        assertThat(first).isEqualTo(Result.OnSuccess(TestUtils.posts))
    }



    @Test
    fun `SavedPosts(posts) saved Success`() = testScope.runBlockingTest {
        repository.savePosts(arrayListOf(Post(4,1),Post(5,1)))
        assertThat(TestUtils.posts).containsAnyIn(arrayListOf(Post(4,1),Post(5,1)))
    }

}
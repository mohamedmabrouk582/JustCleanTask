package com.example.justcleantask.viewModels.posts

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.justcleantask.callBacks.PostsCallBack
import com.example.justcleantask.getOrAwaitValue
import com.example.justcleantask.viewModels.PostsViewModel
import com.example.justcleantask.viewModels.TestUtils
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class PostsViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()
    lateinit var viewModel : PostsViewModel<PostsCallBack>


    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        val app = Mockito.mock(Application::class.java)
        val callback = Mockito.mock(PostsCallBack::class.java)
        viewModel= PostsViewModel(app, FakePostsRepository())
        viewModel.attachView(callback)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `getSavedPosts() return Posts`() {
       val data=viewModel.getSavedPosts().getOrAwaitValue()
        assertThat(viewModel.loader.get()).isFalse()
        assertThat(data).isEqualTo(TestUtils.postsEntity)
    }

    @Test
    fun `removeFavPost(PostEntity)  Remove it Success`() = testScope.runBlockingTest {
        viewModel.unFavPost(PostEntity(1,1,isFav = true))
        val data=viewModel.getSavedPosts().getOrAwaitValue()
        assertThat(data).containsNoneIn(arrayListOf(PostEntity(1,1,isFav = true)))
    }

    @Test
    fun `addPostFav(postEntity)  added Success`() = testScope.runBlockingTest {
       viewModel.addPostAsFav(PostEntity(3,1,isFav = true))
       val data=viewModel.getSavedPosts().getOrAwaitValue()
        assertThat(data).containsAnyIn(arrayListOf(PostEntity(3,1,isFav = true)))
    }

    @Test
    fun `saveOfflineFav(FavPostsOffline) saved success`() = testScope.runBlockingTest {
        viewModel.saveOfflineFav(FavPostsOffline(4,1))
        val first = viewModel.repository.getFavOffline().first()
        assertThat(first).containsAnyIn(arrayListOf(FavPostsOffline(4,1)))
    }

    @Test
    fun `removeOfflineFav(FavPostsOffline) Removed it Success`() = testScope.runBlockingTest{
        viewModel.removeOfflineFav(FavPostsOffline(1,1))
        val first= viewModel.repository.getFavOffline().first()
        assertThat(first).containsNoneIn(arrayListOf(FavPostsOffline(1,1)))
    }

    @Test
    fun `requestAllPosts() Return ListOf Posts`()= testScope.runBlockingTest {
        viewModel.requestPosts()
        assertThat(viewModel.loader.get()).isFalse()
    }

    @Test
    fun `savePosts(posts) saved Success`() = testScope.runBlockingTest {
        val data=viewModel.getSavedPosts().getOrAwaitValue()
        assertThat(data).isEqualTo(TestUtils.postsEntity)
    }
}
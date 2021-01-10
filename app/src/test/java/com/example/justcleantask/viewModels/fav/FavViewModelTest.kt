package com.example.justcleantask.viewModels.fav

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.justcleantask.callBacks.FavCallBack
import com.example.justcleantask.getOrAwaitValue
import com.example.justcleantask.viewModels.FavViewModel
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
class FavViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()
    lateinit var viewModel: FavViewModel<FavCallBack>

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        val callBack= Mockito.mock(FavCallBack::class.java)
        val app = Mockito.mock(Application::class.java)
        viewModel= FavViewModel(app, FakeFavRepository())
        viewModel.attachView(callBack)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `getFavPosts() return Posts`(){
        val data=viewModel.loadFavPosts().getOrAwaitValue()
        assertThat(data).isEqualTo(TestUtils.postsEntity)
    }

    @Test
    fun `removeFavPost(postEntity) remove Success`()=testScope.runBlockingTest {
        viewModel.removeFav(PostEntity(1,1,isFav = true))
        val data=viewModel.loadFavPosts().getOrAwaitValue()
        assertThat(data).containsNoneIn(arrayListOf(PostEntity(1,1,isFav = true)))
    }

    @Test
    fun `removeOfflineFav(post) Removed Success`()= testScope.runBlockingTest {
        viewModel.removeOfflineFav(FavPostsOffline(1,1))
        val data=viewModel.repository.getFavOffline().first()
        assertThat(ArrayList(data)).containsNoneIn(arrayListOf(FavPostsOffline(1,1)))
    }

}
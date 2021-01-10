package com.example.justcleantask.viewModels.comments

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.entities.PostEntity
import com.example.data.utils.toEntity
import com.example.justcleantask.callBacks.CommentsCallBack
import com.example.justcleantask.getOrAwaitValue
import com.example.justcleantask.viewModels.CommentsViewModel
import com.example.justcleantask.viewModels.TestUtils
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CommentsViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()
    lateinit var viewModel : CommentsViewModel<CommentsCallBack>

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        val app= Mockito.mock(Application::class.java)
        val callBack= Mockito.mock(CommentsCallBack::class.java)
        viewModel= CommentsViewModel(app, FakeCommentsRepository())
        viewModel.attachView(callBack)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `getSavedComments() list Of Comments`(){
        val data=viewModel.getSavedComments(1).getOrAwaitValue()
        assertThat(data).isEqualTo(TestUtils.comments.toEntity())
    }

    @Test
    fun `addPostFav(POstEntity) saved Success`() = testScope.runBlockingTest {
        viewModel.addFav(PostEntity(4,1,isFav = true))
        assertThat(TestUtils.postsEntity).containsAnyIn(arrayListOf(PostEntity(4,1,isFav = true)))
    }

    @Test
    fun `removePostFav(postEntity) removed success`()= testScope.runBlockingTest {
        viewModel.removePost(PostEntity(1,1,isFav = true))
        assertThat(TestUtils.postsEntity).containsNoneIn(arrayListOf(PostEntity(1,1,isFav = true)))
    }

    @Test
    fun `requestComments(post_id) return comments List`()= testScope.runBlockingTest {
        viewModel.requestComments(1)
        assertThat(viewModel.loader.get()).isFalse()
    }

    @Test
    fun `saveComments(list) saved Success`() = testScope.runBlockingTest {
        viewModel.repository.saveComments(TestUtils.comments)
        val data=viewModel.getSavedComments(1).getOrAwaitValue()
        assertThat(data).isEqualTo(TestUtils.comments.toEntity())
    }

}
package com.example.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.data.db.PostsDao
import com.example.data.db.PostsDb
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
@ExperimentalCoroutinesApi
class PostsDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()

    @Inject
    @Named("test_db")
    lateinit var db:PostsDb

    lateinit var dao:PostsDao

    @Before
    fun setup(){
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        dao=db.getDao()
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
        db.close()
    }

    @Test
    fun savePosts_savedDone() = testScope.runBlockingTest{
        dao.savePosts(arrayListOf(PostEntity(1,1),
            PostEntity(2,1),PostEntity(3,1)
        ))
        //get Saved Posts from Room Db
        val data = dao.getSavedPosts().getOrAwaitValue()

        assertThat(data).isEqualTo(arrayListOf(PostEntity(1,1),
            PostEntity(2,1),PostEntity(3,1)
        ))
    }

    @Test
    fun saveComments_savedDone()= testScope.runBlockingTest {
        dao.saveComments(arrayListOf(CommentEntity(1,1),
            CommentEntity(2,1), CommentEntity
        (3,1)
        ))

        //get Saved Comments from Room Db (test Select  Query )
        val data= dao.getPostComments(1).getOrAwaitValue()
        assertThat(data).isEqualTo(arrayListOf(CommentEntity(1,1),
            CommentEntity(2,1), CommentEntity
                (3,1)
        ))
    }

    @Test
    fun getFavPosts() = testScope.runBlockingTest{
        dao.savePosts(arrayListOf(PostEntity(1,1),
            PostEntity(2,1,isFav = true),PostEntity(3,1)
        ))

        val data = dao.getFavPosts().getOrAwaitValue()

        assertThat(data).isEqualTo(arrayListOf(PostEntity(2,1,isFav = true)))
    }

    @Test
    fun Add_Post_As_Fav_saved() = testScope.runBlockingTest{
        dao.addPostAsFav(PostEntity(1,1,isFav = true))

        val data = dao.getFavPosts().getOrAwaitValue()

        assertThat(data).containsAnyIn(arrayListOf(PostEntity(1,1,isFav = true)))
    }

    @Test
    fun removePost_from_fav_list() = testScope.runBlockingTest {
        dao.savePosts(arrayListOf(PostEntity(1,1),
            PostEntity(2,1,isFav = true),PostEntity(3,1)
        ))

        dao.removeFavPost(PostEntity(2,1))

        val data = dao.getFavPosts().getOrAwaitValue()

        assertThat(data).isEmpty()
    }

    @Test
    fun save_Fav_offline()= testScope.runBlockingTest {
        dao.favOffline(FavPostsOffline(1,1))
        val first = dao.getFavOffline().first()
        assertThat(ArrayList(first)).isEqualTo(arrayListOf(FavPostsOffline(1,1)))
    }

    @Test
    fun remove_fav_offline_upload_to_server() = testScope.runBlockingTest {
        dao.favOffline(FavPostsOffline(1,1))
        dao.removeFavOffline(FavPostsOffline(1,1))
        val first = dao.getFavOffline().first()
        assertThat(ArrayList(first)).isEmpty()
    }

}
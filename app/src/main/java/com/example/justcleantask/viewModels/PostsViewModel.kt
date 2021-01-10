package com.example.justcleantask.viewModels

import android.app.Application
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.repository.posts.PostsDefaultRepository
import com.example.data.utils.toOffline
import com.example.domain.models.Post
import com.example.domain.utils.Result
import com.example.justcleantask.callBacks.PostsCallBack
import com.example.justcleantask.viewModels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel<V: PostsCallBack> @ViewModelInject constructor(app:Application , val repository: PostsDefaultRepository) : BaseViewModel<V>(app) {
    suspend fun requestPosts(){
        repository.requestAllPosts().collect {
            when(it){
                is Result.OnLoading -> {loader.set(true);error.set(null)}
                is Result.OnSuccess -> {
                       repository.savePosts(it.data)
                       view.loadSavedPosts(getSavedPosts())
                }
                is Result.OnFailure -> {loader.set(false);error.set(it.throwable.message)}
                is Result.NoInternetConnect ->{loader.set(false);view.noNetwork(it.error);view.loadSavedPosts(getSavedPosts())}
            }
        }
    }

    fun getSavedPosts() : LiveData<List<PostEntity>> {
       return repository.getSavedPosts()
    }

    suspend fun unFavPost(post: PostEntity){
            repository.removeFavPost(post)
    }

   suspend fun addPostAsFav(post:PostEntity){
           repository.addPostFav(post)
    }

    override fun retry() {
        viewModelScope.launch {requestPosts()}
    }

    suspend fun saveOfflineFav(post:FavPostsOffline){
            repository.saveOfflineFav(post)
    }

    suspend fun removeOfflineFav(post:FavPostsOffline){
            repository.removeOfflineFav(post)
    }


}
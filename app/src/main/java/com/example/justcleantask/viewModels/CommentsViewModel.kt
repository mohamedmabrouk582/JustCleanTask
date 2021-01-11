package com.example.justcleantask.viewModels

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.repository.comment.CommentsDefaultRepository
import com.example.data.utils.toEntity
import com.example.data.utils.toOffline
import com.example.domain.utils.Result
import com.example.justcleantask.callBacks.CommentsCallBack
import com.example.justcleantask.viewModels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsViewModel<V:CommentsCallBack> @ViewModelInject constructor(app:Application , val repository: CommentsDefaultRepository) : BaseViewModel<V>(app) {
  val post : ObservableField<PostEntity> = ObservableField()
  suspend fun requestComments(post_id:Int){
    repository.requestComments(post_id).collect {
      when(it){
        is Result.OnSuccess -> {
          loader.set(false)
          view.loadComments(it.data.toEntity())
          repository.saveComments(it.data)
        }
        is Result.OnFailure -> {
          loader.set(false)
          error.set(it.throwable.message)
        }
        is Result.OnLoading -> {
          loader.set(true)
          error.set(null)
        }
        is Result.NoInternetConnect ->  {
          loader.set(false)
          view.noNetwork(it.error)
          view.loadSavedComments(getSavedComments(post_id))
        }
      }
    }
  }

  fun getSavedComments(post_id: Int):LiveData<List<CommentEntity>>{
    return repository.getSavedComments(post_id)
  }



  suspend fun addFav(post:PostEntity){
      repository.addPostFav(post)
  }

  suspend fun saveOfflineFav(post:FavPostsOffline){
    repository.saveOfflineFav(post)
  }

  suspend fun removePost(post:PostEntity){
      repository.removePostFav(post)
  }

  suspend fun removeOfflineFav(post:FavPostsOffline){
    repository.removeOfflineFav(post)
  }

  override fun retry() {
    viewModelScope.launch {
      requestComments(post.get()?.id!!)
    }
  }

  val clickListener = View.OnClickListener{
    post.get()?.let {
      viewModelScope.launch {
        if (it.isFav){
          it.isFav=false
          if (offline())removeOfflineFav(it.toOffline())
          removePost(it)
        }else {
          it.isFav=true
          if (offline())saveOfflineFav(it.toOffline())
          addFav(it)
        }
        view.changeIcon(it.isFav)
      }
    }
  }


}
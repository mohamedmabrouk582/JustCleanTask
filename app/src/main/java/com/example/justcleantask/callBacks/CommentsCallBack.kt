package com.example.justcleantask.callBacks

import androidx.lifecycle.LiveData
import com.example.data.entities.CommentEntity

interface CommentsCallBack : BaseCallBack {
    fun loadComments(comments:ArrayList<CommentEntity>)
    fun loadSavedComments(comments: LiveData<List<CommentEntity>>)
    fun noNetwork(error:String)
    fun changeIcon(isFav:Boolean)
}
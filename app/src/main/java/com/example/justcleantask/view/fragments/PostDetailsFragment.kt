package com.example.justcleantask.view.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.data.entities.CommentEntity
import com.example.data.entities.PostEntity
import com.example.domain.models.Post
import com.example.justcleantask.R
import com.example.justcleantask.callBacks.CommentsCallBack
import com.example.justcleantask.databinding.PostDetailsLayoutBinding
import com.example.justcleantask.view.POST_KEY
import com.example.justcleantask.view.adapter.CommentsAdapter
import com.example.justcleantask.viewModels.CommentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.http.POST

@AndroidEntryPoint
class PostDetailsFragment : Fragment(R.layout.post_details_layout) , CommentsCallBack {
    lateinit var viewBinding:PostDetailsLayoutBinding
    val viewModel : CommentsViewModel<CommentsCallBack> by viewModels()
    val adapter:CommentsAdapter by lazy { CommentsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBindingUtil.bind<PostDetailsLayoutBinding>(view)?.let { viewBinding=it }
        viewModel.attachView(this)
        viewBinding.vm=viewModel
        viewBinding.recyclerView.adapter=adapter
        arguments?.getParcelable<PostEntity>(POST_KEY)?.let {
            viewModel.post.set(it)
            lifecycleScope.launch {
                viewModel.requestComments(it.id)
            }
            viewModel.upload()
        }
    }

    override fun loadComments(comments: ArrayList<CommentEntity>) {
       adapter.data=comments
    }

    override fun loadSavedComments(comments: LiveData<List<CommentEntity>>) {
       comments.observe(viewLifecycleOwner,{
           adapter.data= ArrayList(it)
       })
    }

    override fun noNetwork(error: String) {
        Snackbar.make(viewBinding.root,error, Snackbar.LENGTH_SHORT).show()
    }

    override fun changeIcon(isFav: Boolean) {
        viewBinding.fav.setImageDrawable(if(isFav) resources.getDrawable(R.drawable.ic_book) else resources.getDrawable(R.drawable.ic_un_book))
    }
}
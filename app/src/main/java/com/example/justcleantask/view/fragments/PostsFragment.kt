package com.example.justcleantask.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import com.example.data.api.PostsApi
import com.example.data.entities.PostEntity
import com.example.data.utils.toOffline
import com.example.justcleantask.R
import com.example.justcleantask.callBacks.PostsCallBack
import com.example.justcleantask.databinding.PostsFragmentLayoutBinding
import com.example.justcleantask.view.MainActivity
import com.example.justcleantask.view.POST_KEY
import com.example.justcleantask.view.adapter.PostsAdapter
import com.example.justcleantask.viewModels.PostsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.posts_fragment_layout), PostsAdapter.PostListener , PostsCallBack {
    lateinit var viewBinding:PostsFragmentLayoutBinding
    val viewModel : PostsViewModel<PostsCallBack> by viewModels()
    val adapter: PostsAdapter by lazy { PostsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBindingUtil.bind<PostsFragmentLayoutBinding>(view)?.let { viewBinding=it }
        viewModel.attachView(this)
        val alphaAdapter = AlphaInAnimationAdapter(adapter)
        viewBinding.recyclerView.adapter= ScaleInAnimationAdapter(alphaAdapter)
        viewBinding.postsVm=viewModel
        lifecycleScope.launch {
            viewModel.requestPosts()
        }
        viewModel.upload()
    }

    override fun loadSavedPosts(posts: LiveData<List<PostEntity>>) {
        viewModel.loader.set(false)
       posts.observe(viewLifecycleOwner,{
           adapter.data= ArrayList(it)
       })
    }

    override fun noNetwork(error: String) {
        Snackbar.make(viewBinding.root,error,Snackbar.LENGTH_SHORT).show()
    }

    override fun onPOstClick(item: PostEntity, pos: Int) {
        val bundle = Bundle()
        bundle.putParcelable(POST_KEY,item)
        (activity as MainActivity).navController.navigate(R.id.action_postsFragment_to_postDetailsFragment,bundle)
    }

    override fun OnBookClick(item: PostEntity, pos: Int) {
        lifecycleScope.launch {
            if (item.isFav){
                if (viewModel.offline())viewModel.removeOfflineFav(item.toOffline())
                viewModel.unFavPost(item)
            }else {
                if (viewModel.offline())viewModel.saveOfflineFav(item.toOffline())
                 viewModel.addPostAsFav(item)
            }
        }
        adapter.update(pos)

    }
}
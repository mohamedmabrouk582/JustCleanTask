package com.example.justcleantask.view.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.data.entities.PostEntity
import com.example.data.utils.toOffline
import com.example.justcleantask.R
import com.example.justcleantask.callBacks.FavCallBack
import com.example.justcleantask.databinding.FavoritesFragmentLayoutBinding
import com.example.justcleantask.view.adapter.PostsAdapter
import com.example.justcleantask.viewModels.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment_layout)  , FavCallBack,
    PostsAdapter.PostListener {
    lateinit var viewBinding : FavoritesFragmentLayoutBinding
    val viewmodel : FavViewModel<FavCallBack> by viewModels()
    val adapter: PostsAdapter by lazy { PostsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBindingUtil.bind<FavoritesFragmentLayoutBinding>(view)?.let { viewBinding=it }
        viewmodel.attachView(this)
        val alphaAdapter = AlphaInAnimationAdapter(adapter)
        viewBinding.favRcv.adapter= ScaleInAnimationAdapter(alphaAdapter)
        viewBinding.favVm=viewmodel
        loadFavPosts(viewmodel.loadFavPosts())
    }

    override fun loadFavPosts(fav: LiveData<List<PostEntity>>) {
        viewmodel.error.set("no fav posts")
      fav.observe(viewLifecycleOwner,{fav ->
          viewmodel.error.set(null)
         adapter.data= ArrayList(fav)
      })
    }

    override fun onPOstClick(item: PostEntity, pos: Int) {

    }

    override fun OnBookClick(item: PostEntity, pos: Int) {
        lifecycleScope.launch {
            viewmodel.removeFav(item)
            if(viewmodel.offline())viewmodel.removeOfflineFav(item.toOffline())
        }
         adapter.removeFav(pos)
    }

}
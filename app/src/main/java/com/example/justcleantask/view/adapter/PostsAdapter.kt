package com.example.justcleantask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entities.PostEntity
import com.example.justcleantask.R
import com.example.justcleantask.databinding.PostItemViewBinding

class PostsAdapter(val listener : PostListener) : RecyclerView.Adapter<PostsAdapter.Holder>() {
    var data:ArrayList<PostEntity> = ArrayList()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<PostItemViewBinding>(LayoutInflater.from(parent.context),
            R.layout.post_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    fun update(pos:Int){
        try {
            data[pos].isFav= !data[pos].isFav
            notifyItemChanged(pos)
        } catch (e:Exception){}

    }

    fun removeFav(pos:Int){
        try {
            data[pos].isFav= false
            data.removeAt(pos)
            notifyItemRemoved(pos)
        } catch (e:Exception){}
    }

    override fun getItemCount(): Int = data.size

    inner class Holder(val viewBinding:PostItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item:PostEntity){
            viewBinding.post=item
            viewBinding.root.setOnClickListener { listener.onPOstClick(item,adapterPosition) }
            viewBinding.bookImg.setOnClickListener { listener.OnBookClick(item,adapterPosition) }
            viewBinding.executePendingBindings()
        }
    }

    interface PostListener{
        fun onPOstClick(item: PostEntity,pos:Int)
        fun OnBookClick(item: PostEntity,pos:Int)
    }
}
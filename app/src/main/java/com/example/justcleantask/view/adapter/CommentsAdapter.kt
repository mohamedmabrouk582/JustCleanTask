package com.example.justcleantask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entities.CommentEntity
import com.example.justcleantask.R
import com.example.justcleantask.databinding.CommentItemViewBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.Holder>(){
   var data:ArrayList<CommentEntity> = ArrayList()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<CommentItemViewBinding>(LayoutInflater.from(parent.context),
        R.layout.comment_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size




    inner class Holder(private val viewBinding:CommentItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item:CommentEntity){
            viewBinding.comment=item
            viewBinding.executePendingBindings()
        }
    }

}
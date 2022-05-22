package com.hazz.kotlinmvp.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.hazz.kotlinmvp.R
import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.mvp.model.bean.CommentBean
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.view.recyclerview.adapter.CommonAdapter

class CommentAdapter(context: Context, dataList: ArrayList<CommentBean.Row>) : CommonAdapter<CommentBean.Row>(context, dataList, R.layout.comment_viewholder_layout) {

    override fun bindData(holder: ViewHolder, data: CommentBean.Row, position: Int) {
        setCommentInfo(holder,data)
    }

    fun setCommentInfo(holder: ViewHolder, data: CommentBean.Row){
        holder.setText(R.id.username_tv,data.userId.toString())
        holder.setText(R.id.comment_tv,data.content)
        if(data.userId.toString() != UserManager.username){
            holder.setViewVisibility(R.id.delete_iv,ViewGroup.GONE)
        }
    }

    fun addData(dataList: ArrayList<CommentBean.Row>) {
        val position = itemCount
        this.mData.addAll(dataList)
        notifyItemInserted(position)
    }

    fun addData(row: CommentBean.Row){
        val position = itemCount
        this.mData.add(row)
        notifyItemInserted(itemCount)
    }
}
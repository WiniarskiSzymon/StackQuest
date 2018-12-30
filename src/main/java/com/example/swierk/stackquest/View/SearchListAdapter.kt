package com.example.swierk.stackquest.View

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swierk.stackquest.R
import com.example.swierk.stackquest.model.Question
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.question_item.view.*


class SearchListAdapter(val items: List<Question>,private val clickListener: (Question) -> Unit) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.question_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateWithUrl(items[position].owner.profileImage)
        holder.answerCount.text = items[position].answerCount.toString()
        holder.title.text = items[position].title
        holder.userName.text = items[position].owner.displayName
        holder.itemView.setOnClickListener{clickListener(items[position])}

    }


    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userAvatar = view.avatar
        val answerCount = view.counts_amount
        val title = view.title
        val userName = view.user_name
        fun updateWithUrl(url: String) {
            Picasso.get().load(url).into(userAvatar)
        }
    }
}
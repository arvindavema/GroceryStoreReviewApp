package com.example.fromsscratch

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text


internal class MyReviewListViewAdapter (
    private val mReviews: List<Review>,
    private val mRowLayout: Int
    ) : RecyclerView.Adapter<MyReviewListViewAdapter.ViewHolder>(){
    // Create ViewHolder which holds a View to be displayed
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(mRowLayout, viewGroup, false)
        return ViewHolder(v)
    }

    // Binding: The process of preparing a child view to display data corresponding to a position within the adapter.
    override fun onBindViewHolder(vh: ViewHolder, i: Int) {
        val r = mReviews[i]
        vh.mAuthor.text =r.username
        vh.mBody.text = r.body
        vh.mDate.text = r.date
        vh.mStore.text = r.storeName
        vh.mRating.rating = r.rating?.toFloat() ?: 0f
    }

    override fun getItemCount(): Int {
        return mReviews.size
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal val mAuthor: TextView = itemView.findViewById(R.id.authorName)
        internal val mStore: TextView = itemView.findViewById(R.id.storeName)
        internal val mRating: RatingBar = itemView.findViewById(R.id.ratingBar)
        internal val mBody: TextView = itemView.findViewById(R.id.reviewBody)
        internal val mDate: TextView = itemView.findViewById(R.id.datePosted)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }


}
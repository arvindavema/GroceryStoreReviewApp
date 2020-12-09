package com.example.fromsscratch

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView
import java.sql.Timestamp


internal class MyReviewList (private val context: Activity, private var mReviews: List<Review>) : ArrayAdapter<Review>(context, R.layout.review_list_item,mReviews) {

    @SuppressLint("ViewHolder")
    override fun getView(   position: Int,    convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val itemView = inflater.inflate(R.layout.review_list_item,parent,false)

        var mAuthor: TextView = itemView.findViewById(R.id.authorName)
        val mStore: TextView = itemView.findViewById(R.id.storeName)
        val mRating: RatingBar = itemView.findViewById(R.id.ratingBar)
        val mBody: TextView = itemView.findViewById(R.id.reviewBody)
        val mDate: TextView = itemView.findViewById(R.id.datePosted)


        val review = mReviews[position]


        mAuthor.text = review.username
        mStore.text = review.storeName
        mDate.text = review.timeStamp
        mBody.text = review.body
        mRating.rating = review.rating!!.toFloat()

        return itemView
    }

//    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        internal val mAuthor: TextView = itemView.findViewById(R.id.authorName)
//        internal val mStore: TextView = itemView.findViewById(R.id.storeName)
//        internal val mRating: RatingBar = itemView.findViewById(R.id.ratingBar)
//        internal val mBody: TextView = itemView.findViewById(R.id.reviewBody)
//        internal val mDate: TextView = itemView.findViewById(R.id.datePosted)
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        override fun onClick(view: View) {
//        }
//    }



}
package com.example.fromsscratch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)
        //TODO get info from intent, and the rest from the layout
        //TODO use it to make a Review object
        //TODO look at RegisterActivity in saveUser(...) to see how
        //TODO after save, start UserActivity to display the new review
        //TODO at the top. Maybe add a time property to review to sort by order
        //TODO of creation
    }
}
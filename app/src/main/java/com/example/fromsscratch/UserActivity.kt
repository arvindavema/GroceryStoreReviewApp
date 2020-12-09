package com.example.fromsscratch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.widget.*
import com.google.firebase.database.*
import java.util.*


import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.collections.ArrayList


class UserActivity : AppCompatActivity() {
    private lateinit var mReviewListView: ListView
    private lateinit var mCurrUser: FirebaseUser

    private lateinit var mReviews: ArrayList<Review>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mReviewDB:  DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        mAuth = FirebaseAuth.getInstance()
        mReviewListView = findViewById(R.id.reviewListView)
        mReviews = ArrayList()
    }

    override fun onStart() {
        super.onStart()
        mCurrUser = mAuth.currentUser!!
        if (mCurrUser == null) {
            mAuth.signOut()
            startActivity(Intent(this@UserActivity, MainActivity::class.java))
        } else {
            mReviewDB.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) { /* NA */   }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    toast(this@UserActivity, "Review Successfully Posted!")
                    mReviews.clear()

                    for (postSnapshot in dataSnapshot.children) {
                        val review = postSnapshot.getValue(Review::class.java)
                        mReviews.add(review!!)
                    }
                    mReviewListView.adapter = MyReviewList(this@UserActivity, mReviews)

                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.homeItem -> {
                log("starting user from user, do nothing")
                toast(this, "One Moment, Loading Your Home Page...")
                startActivity(Intent(this, UserActivity::class.java))
                true
            }
            R.id.logoutItem -> {
                toast(this, "Signing out : (. Come Back Soon!")

                mAuth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.findStoreItem -> {
                toast(this, "One Moment, Loading Map of Nearby Stores")
                var intent = Intent(this, StoreMapsActivity::class.java)

                startActivity(intent)
                true
            }
            else -> false
        }
    }

    private fun toast(context: Context,msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }


    private fun log(msg: String) {
        Log.d("Proj", msg )
    }
}
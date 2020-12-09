package com.example.fromsscratch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        if(mAuth.currentUser == null) {
            startActivity(Intent(this,MainActivity::class.java))
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
                startActivity(Intent(this, UserActivity::class.java))
                true
            }
            R.id.logoutItem -> {
                mAuth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.findStoreItem -> {
                startActivity(Intent(this, RestaurantActivity::class.java))
                true
            }
            else -> false
        }
    }


    private fun log(msg: String) {
        Log.d("Proj", msg )
    }
}
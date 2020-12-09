package com.example.fromsscratch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button

    private lateinit var mAuth: FirebaseAuth
    private val TAG = "MAIN"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        loginBtn = findViewById(R.id.loginBtn)
        loginBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        registerBtn = findViewById(R.id.registrBtn)
        registerBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }

    }



    override fun onStart() {
        super.onStart()
        if(mAuth?.currentUser != null ) {
            startActivity(Intent(this, UserActivity::class.java))
        }
    }


}
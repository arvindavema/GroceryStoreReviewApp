package com.example.fromsscratch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        log("oncreate from Lofin")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        userEmail = findViewById(R.id.editTextTextEmailAddress)
        userPassword=findViewById(R.id.editTextTextPassword)
        log("end oncreate from Lofin")
    }

    public override fun onStart() {
        super.onStart()
        if(mAuth.currentUser != null){
            startActivity(Intent(this@LoginActivity, UserActivity::class.java))
        }
    }

    fun login(view: View?) {
        log("login")

        val email: String = userEmail.text.toString()
        val password: String = userPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
//            progressBar.visibility = View
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG).show()
                var intent = Intent(this@LoginActivity, UserActivity::class.java)
                intent.putExtra("email", mAuth.currentUser?.email)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Login failed! Please try again later",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun log(msg: String) {
        Log.d("Proj", msg )
    }
}
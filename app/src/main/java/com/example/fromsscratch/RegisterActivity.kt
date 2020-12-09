package com.example.fromsscratch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var username: EditText
    private lateinit var  mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        log("registration on create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        username = findViewById(R.id.editTextTextPersonName)
        email = findViewById(R.id.registerEmaiil)
        password = findViewById(R.id.newPassword)

        log("exit registration on create")

    }


    fun signup(view: View) {
        log("registration signup")

        val validators = Validators()

        val pass = password.text.toString()
        val em = email.text.toString()
        val uname = email.text.toString()

        var image_path = ""

        if (validators.validEmail(em)) {
            if(validators.validPassword(pass)){
                val y = mAuth!!.createUserWithEmailAndPassword(em, pass)
                    y.addOnCompleteListener(
                    OnCompleteListener{ task ->
                    log("registration Add Listeber")

                    if (task.isSuccessful) {
                        val id  = FirebaseAuth.getInstance().currentUser?.uid
                        log("${id }: This is the uid")
                        Toast.makeText(this@RegisterActivity,  "Welcome to Our App!", Toast.LENGTH_LONG).show()
                        log("starting User From Reg")
                        startActivity(Intent(this@RegisterActivity, UserActivity::class.java))
                    } else {
                        log("${mAuth.currentUser?.uid} - ${pass}, ${em}: failed")
                        Toast.makeText(this@RegisterActivity, "Something went wrong. Sorry about that my developers are incompetent", Toast.LENGTH_LONG).show()
                    }
                })
            } else{
                Toast.makeText(applicationContext, "Password Needs 8 characters with 1 letter and 1 number $pass", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(applicationContext, "Invalid email address", Toast.LENGTH_SHORT).show()
        }
    }

    private fun log(msg: String) {
        Log.d("Proj", msg )
    }
}
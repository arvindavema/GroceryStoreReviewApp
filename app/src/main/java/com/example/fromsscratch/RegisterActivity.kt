package com.example.fromsscratch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var username: EditText
    private lateinit var regBtn: Button
    private lateinit var  mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        username = findViewById(R.id.editTextTextPersonName)
        email = findViewById(R.id.registerEmaiil)
        password = findViewById(R.id.newPassword)


        regBtn = findViewById(R.id.submitRegister)
    }


    fun signup(view: View) {
        val validators = Validators()

        val pass = password.text.toString()
        val em = email.text.toString()
        val uname = username.text.toString()

        var image_path = ""

        if (validators.validEmail(em)) {
            if(validators.validPassword(pass)){
                mAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val id  = mAuth.currentUser?.uid

                        saveUser(id , em, uname, image_path)

                        Toast.makeText(applicationContext,  "Welcome to Our App!", Toast.LENGTH_LONG).show()
                        var intent = Intent(this@RegisterActivity, UserActivity::class.java)
                        intent.putExtra("email", mAuth.currentUser?.email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Something went wrong. Sorry about that my developers are incompetent", Toast.LENGTH_LONG).show()
                    }
                }
            } else{
                Toast.makeText(applicationContext, "Password Needs 8 characters with 1 letter and 1 number $pass", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(applicationContext, "Invalid email address", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUser(id: String?, em: String, uname: String, image_path: String){
        var img_path = ""
        if (image_path != "" )
            img_path = image_path

        var user = User(id, em, uname, img_path)

        var database = FirebaseDatabase.getInstance().reference

        database.child("users").child(id.toString()).setValue(user)
    }


}
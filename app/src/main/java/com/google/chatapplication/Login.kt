package com.google.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail :EditText
    private lateinit var edtPassword :EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp :Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         edtEmail = findViewById(R.id.edt_email)
         edtPassword = findViewById(R.id.edt_password)
        btnLogin= findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signUP)
        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)

        }


        btnLogin.setOnClickListener {
            val email =edtEmail.text.toString()
            val password = edtPassword.text.toString()
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {

        //logic for login the user
        when {
            TextUtils.isEmpty(edtEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@Login,
                    "Please enter email.",
                    Toast.LENGTH_SHORT
                ).show()
            }
//
//            TextUtils.isEmpty(binding?.etLoginPassword?.text.toString().trim { it <= ' ' }) -> {
//                Toast.makeText(
//                    this@LoginActivity,
//                    "Please enter password.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            else -> {

                val email: String = edtEmail.text.toString().trim { it <= ' ' }
//                val password: String = binding?.etLoginPassword?.text.toString().trim { it <= ' ' }

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@Login, MainActivity::class.java)
                            finish()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@Login, "user does not exit", Toast.LENGTH_LONG)
                                .show()

                        }
                    }
            }

        }
    }
    }



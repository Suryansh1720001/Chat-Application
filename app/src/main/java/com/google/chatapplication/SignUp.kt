package com.google.chatapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {



    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtName : EditText

    private lateinit var btnSignUp : Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName= findViewById(R.id.edt_name)

        btnSignUp = findViewById(R.id.btn_signUP)
        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()

            signUp(name,email,password)
        }


    }

    private fun signUp(name:String,email: String, password: String) {

        when {
            TextUtils.isEmpty(edtEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@SignUp,
                    "Please enter email.",
                    Toast.LENGTH_SHORT
                ).show()
            }

//            TextUtils.isEmpty(binding?.etRegisterPassword?.text.toString().trim { it <= ' ' }) -> {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Please enter password.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            else -> {

                val email: String = edtEmail.text.toString().trim { it <= ' ' }
//                val password: String = binding?.etRegisterPassword?.text.toString().trim { it <= ' ' }


                // logic of creating user
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(
                                this@SignUp,
                                "You are registered successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // code for jumping to home activity
                            val intent = Intent(this@SignUp, MainActivity::class.java)
                            finish()
                            startActivity(intent)

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")

                            addUserTODatabase(name,email,mAuth.currentUser?.uid!!)


                        } else {
//                    Toast.makeText(this@SignUp, "Some error occuresd",Toast.LENGTH_LONG).show()


                            Toast.makeText(
                                this@SignUp,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()


                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }

        }
    }

    private fun addUserTODatabase(name: String, email: String, uid: String?) {

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid!!).setValue(User(name,email,uid))




    }
}
package com.example.gogreen

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.gogreen.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase

class SignUpPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var auth: FirebaseAuth
    private var email: String = ""
    private var username: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
        database = Firebase.database.reference

        email = findViewById<EditText>(R.id.textRegisterEmail).text.toString()
        //username = findViewById<EditText>(R.id.textRegisterName).text.toString()
        password = findViewById<EditText>(R.id.textRegisterPass).text.toString()

        val btnRegister: Button = findViewById(R.id.registerBtn)


        btnRegister.setOnClickListener(){
            email = findViewById<EditText>(R.id.textRegisterEmail).text.toString()
            //username = findViewById<EditText>(R.id.textRegisterName).text.toString()
            password = findViewById<EditText>(R.id.textRegisterPass).text.toString()

            if(email.isEmpty()){
                findViewById<EditText>(R.id.textRegisterEmail).error = "Email is required!"
                findViewById<EditText>(R.id.textRegisterEmail).requestFocus()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                findViewById<EditText>(R.id.textRegisterEmail).error = "Valid email is required!"
                findViewById<EditText>(R.id.textRegisterEmail).requestFocus()
            }
//            else if(username.isEmpty()){
//                findViewById<EditText>(R.id.textRegisterName).error = "Username is required!"
//                findViewById<EditText>(R.id.textRegisterName).requestFocus()
//            }
            else if(password.isEmpty()){
                findViewById<EditText>(R.id.textRegisterPass).error = "Password is required!"
                findViewById<EditText>(R.id.textRegisterPass).requestFocus()
            }else if (password.length < 6 ) {
                findViewById<EditText>(R.id.textRegisterPass).error = "Password is too short!"
                findViewById<EditText>(R.id.textRegisterPass).requestFocus()
            }
            else
            {
                createAccount(email, password)
            }
        }
    }


    public override fun onStart(){
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }

    }

    private fun createAccount(email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    finish()
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Registration successful.",Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser

                  // writeNewUser(user?.uid.toString(), username, email)


                    //updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Registration failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }

    }



    private fun writeNewUser(userId: String, username: String, email: String){
        val user = User(username, email)


        database.child("users").child(userId).setValue(user).addOnCompleteListener(this){
            task->
            if(task.isSuccessful){
                Toast.makeText(baseContext, "Pog.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(baseContext, "Not Pog.",Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun reload(){

    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            finish()
        }else{

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("username", username)
        outState.putString("email", email)
        outState.putString("password", password)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getString("username", username)
        savedInstanceState.putString("email", email)
        savedInstanceState.putString("password", password)
    }

    override fun onBackPressed() {

    }
}
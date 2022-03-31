package com.example.gogreen

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gogreen.models.userLogged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth

        email = findViewById<EditText>(R.id.textLoginEmail).text.toString()
        password = findViewById<EditText>(R.id.textLoginPass).text.toString()

        val btnRegister: Button = findViewById(R.id.toRegisterBtn)

        btnRegister.setOnClickListener(){
            val intent: Intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }

        val textEmail: EditText = findViewById(R.id.textLoginEmail)
        val textPass: EditText = findViewById(R.id.textLoginPass)

        val btnLogin: Button = findViewById(R.id.loginBtn)

        btnLogin.setOnClickListener(){

            email = findViewById<EditText>(R.id.textLoginEmail).text.toString()
            password = findViewById<EditText>(R.id.textLoginPass).text.toString()


            if(email.isEmpty()){
                textEmail.error = "Email is required!"
                textEmail.requestFocus()
            }
            else if(password.isEmpty()){
                textPass.error = "Password is required!"
                textPass.requestFocus()
            }
            else
            {
                signIn(email, password)
            }


        }





    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)

                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val i = Intent(this, Main::class.java)
        if (user != null) {
            i.putExtra("email", user.email)
        }
        userLogged.currentUser = user;
        startActivity(i)
    }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun reload(){

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("email", email)
        outState.putString("password", password)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.putString("email", email)
        savedInstanceState.putString("password", password)
    }

    override fun onBackPressed() {

    }
}
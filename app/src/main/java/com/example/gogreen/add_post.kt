package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gogreen.models.Posts
import com.example.gogreen.models.userLogged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

private lateinit var auth: FirebaseAuth
private lateinit var database: DatabaseReference
private lateinit var databaseU: DatabaseReference

class add_post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val caption: EditText = findViewById(R.id.textCaption)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
        databaseU = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")

        val btnShare: Button = findViewById(R.id.btnShare)

        btnShare.setOnClickListener(){

            var text = ""
            text = caption.text.toString()

            addPost(text)

//            database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
//            database.child("currentCount").get()
//                .addOnSuccessListener { donateCount ->
//                    var donoCount: Int = 0
//                    donoCount = donateCount.value.toString().toInt()
//                    userLogged.postList.clear()
//                    for (i in donoCount downTo 1) {
//                        database.child(i.toString()).get()
//                            .addOnSuccessListener {
//                                var name = it.child("name").value.toString()
//                                var time = it.child("time").value.toString()
//                                var caption = it.child("caption").value.toString()
//                                var likes = it.child("likes").value.toString().toInt()
//
//                                userLogged.postList.add(Posts(name,caption, time, likes))
//
//                            }
//                    }
//
//                }

            finish()

        }

    }

    private fun addPost(text:String){

        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
        databaseU = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")

        database.child("currentCount").get()
            .addOnSuccessListener {
                var count = 0
                count = it.value.toString().toInt()

                count++
                database.child("currentCount").setValue(count)


                databaseU.child("userTable").child(auth.currentUser?.uid.toString()).get()
                    .addOnSuccessListener { user->
                        var name = ""
                        name = user.child("username").value.toString()

                        var sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        var time = sdf.format(Date())

                        var caption = text

                        var post = Posts(name,caption,time,0)

                        database.child(count.toString()).setValue(post)

                        Toast.makeText(baseContext, "Post shared!", Toast.LENGTH_SHORT).show()
                    }


            }
    }
}
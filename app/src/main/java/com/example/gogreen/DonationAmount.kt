package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gogreen.models.DonoHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class DonationAmount : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    private lateinit var database: DatabaseReference
    private lateinit var databaseU: DatabaseReference

    var donationCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_amount)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("DonationDB")
        databaseU = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")

        auth = Firebase.auth

        var currentAmount: Double = 0.0
        database.child("DonationTotal").get()
            .addOnSuccessListener {
                currentAmount = it.getValue<Double>()!!
            }

        var donoAmount: Double = 0.0

        val textDonoAmount: EditText = findViewById(R.id.textDonoTotal)
        donoAmount = textDonoAmount.text.toString().toDouble()




        val btnConfirm: Button = findViewById(R.id.btnConfirmDono)


        btnConfirm.setOnClickListener(){


            if(textDonoAmount.text.toString().isEmpty()){
                textDonoAmount.error = "Please enter a valid amount!"
                textDonoAmount.requestFocus()
            }
            else {
                donoAmount = textDonoAmount.text.toString().toDouble()
                addAmount(donoAmount, currentAmount)
                finish()
            }

        }


    }

    fun addAmount(amount: Double, currentAmount: Double){


        var newAmount: Double = currentAmount + amount

        database.child("DonationTotal").setValue(newAmount)
            .addOnSuccessListener {
                if(amount > 0.0){
                    Toast.makeText(baseContext, "Donation Successful! Amount: RM " + String.format("%.2f", amount), Toast.LENGTH_SHORT).show()


                    databaseU.child("userTable").child(auth.currentUser?.uid.toString()).child("donationCount").get()
                        .addOnSuccessListener {
                            donationCount = it.value.toString().toInt()
                            donationCount++
                            databaseU.child("userTable").child(auth.currentUser?.uid.toString()).child("donationCount").setValue(donationCount)

                        }
                        .addOnFailureListener {
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }

                    databaseU.child("userTable").child(auth.currentUser?.uid.toString()).child("donationCount").get()
                        .addOnSuccessListener {
                            var donationNo = it.value.toString().toInt() + 1
                            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            val donoHis = DonoHistory(amount, currentDate)
                            databaseU.child("userTable").child(auth.currentUser?.uid.toString()).child("donationHistory").child(donationNo.toString()).setValue(donoHis)

                        }
                        .addOnFailureListener {
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }



                }else {
                    Toast.makeText(baseContext, "No Donation is Made. Amount: RM " + String.format("%.2f", amount), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Donation Failed!" + it.message,Toast.LENGTH_SHORT).show()
            }

    }
}
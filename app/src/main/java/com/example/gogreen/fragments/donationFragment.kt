package com.example.gogreen.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.gogreen.R
import com.example.gogreen.models.donations
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [donationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class donationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


//        var btnTest: Button = requireView().findViewById(R.id.btnInsert)
//        btnTest.setOnClickListener(){
//            database.setValue("Hello World!")
//        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donation, container, false)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment donationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            donationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private val model: donations by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database.getReference("message")

        val tvDonoTotal: TextView = requireView().findViewById(R.id.tvDonoTotal)

        val donationObserver = Observer<Double>{donoTotal ->
            tvDonoTotal.setText(String.format("%.2f", donoTotal))
        }

        model.donationTotal.observe(this,donationObserver)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object: Runnable{
            override fun run(){
               updateDonoTotal()
                mainHandler.postDelayed(this, 100)
            }
        })



    }

    fun updateDonoTotal(){
        val myRef = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("DonationDB")


        myRef.child("DonationTotal").get()
            .addOnSuccessListener {
            model.donationTotal.value = it.getValue<Double>()
            }


    }



}
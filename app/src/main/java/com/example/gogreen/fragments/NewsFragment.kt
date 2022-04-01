package com.example.gogreen.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gogreen.R
import com.example.gogreen.add_post
import com.example.gogreen.fragments.adapters.PostRecycleAdapter
import com.example.gogreen.models.Activitys
import com.example.gogreen.models.Posts
import com.example.gogreen.models.userLogged
import com.example.gogreen.models.userLogged.postList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [newsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class newsFragment : Fragment() , PostRecycleAdapter.OnItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment newsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            newsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
        database.child("currentCount").get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                donoCount = donateCount.value.toString().toInt()
                userLogged.postList.clear()
                for (i in donoCount downTo 1) {
                    database.child(i.toString()).get()
                        .addOnSuccessListener {
                            var name = it.child("name").value.toString()
                            var time = it.child("time").value.toString()
                            var caption = it.child("caption").value.toString()
                            var likes = it.child("likes").value.toString().toInt()

                            userLogged.postList.add(Posts(name,caption, time, likes))

                        }
                }

            }

        val myAdapter = PostRecycleAdapter(userLogged.postList, this)
        val mainHandler = Handler(Looper.getMainLooper())
        val myRecycler: RecyclerView = requireView().findViewById(R.id.postRecycler)

        myRecycler.adapter = myAdapter
        myRecycler.layoutManager = LinearLayoutManager(context)
        myRecycler.setHasFixedSize(true)

        val fab: View = requireView().findViewById(R.id.fabPost)

        fab.setOnClickListener(){
            var i: Intent = Intent(activity, add_post::class.java)
            startActivity(i)
        }

        val fab1: View = requireView().findViewById(R.id.fabRefresh)

        fab1.setOnClickListener(){

            database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
            database.child("currentCount").get()
                .addOnSuccessListener { donateCount ->
                    var donoCount: Int = 0
                    donoCount = donateCount.value.toString().toInt()
                    userLogged.postList.clear()
                    for (i in donoCount downTo 1) {
                        database.child(i.toString()).get()
                            .addOnSuccessListener {
                                var name = it.child("name").value.toString()
                                var time = it.child("time").value.toString()
                                var caption = it.child("caption").value.toString()
                                var likes = it.child("likes").value.toString().toInt()

                                userLogged.postList.add(Posts(name,caption, time, likes))

                            }
                    }

                }

            val myAdapter = PostRecycleAdapter(userLogged.postList, this)

            myRecycler.adapter = myAdapter
            myRecycler.layoutManager = LinearLayoutManager(context)
            myRecycler.setHasFixedSize(true)
        }

        myRecycler.adapter!!.notifyDataSetChanged()

    }


    override fun itemClick(position: Int) {
        val selectPost = postList[position]

        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
        database.child((postList.size-position).toString()).child("likes").setValue(selectPost.likes+1)






    }
}
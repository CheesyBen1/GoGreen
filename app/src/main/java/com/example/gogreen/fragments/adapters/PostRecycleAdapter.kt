package com.example.gogreen.fragments.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gogreen.R
import com.example.gogreen.models.Posts
import com.example.gogreen.models.postModel
import com.google.firebase.database.FirebaseDatabase


class PostRecycleAdapter(private val postList: List<Posts>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PostRecycleAdapter.MyViewHolder>() {



    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        var tvName: TextView = view.findViewById(R.id.tvPostName)
        var tvTime: TextView = view.findViewById(R.id.tvPostTime)
        var tvCaption: TextView = view.findViewById(R.id.tvPostCaption)
        var tvLikes: TextView = view.findViewById(R.id.tvPostLikes)

        var btnLike: Button = view.findViewById(R.id.btnLike)



        init{
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.itemClick(position)
            }
        }


    }

    interface OnItemClickListener{
        fun itemClick(position: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRec = postList[position]

        holder.tvName.text = currentRec.name
        holder.tvTime.text = currentRec.time
        holder.tvCaption.text = currentRec.caption
        holder.tvLikes.text = currentRec.likes.toString()

        holder.btnLike.isClickable = false


    }



    override fun getItemCount(): Int {
        return postList.size
    }




}
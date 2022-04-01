package com.example.gogreen.fragments.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gogreen.R
import com.example.gogreen.fragments.activitiesFragment
import com.example.gogreen.joinedDetails
import com.example.gogreen.models.Activitys
import com.example.gogreen.models.postModel
import com.example.gogreen.models.userLogged
import kotlin.random.Random

class JoinedRecycleAdapter(private val activityList: List<Activitys> , private val listener:OnItemClickListener) : RecyclerView.Adapter<JoinedRecycleAdapter.MyViewHolder>(){



    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var tvName: TextView = view.findViewById(R.id.tvActivityName)
        var layout: View = view.findViewById(R.id.ActivityLayout)

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View){
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRec = activityList[position]

        holder.tvName.text = currentRec.Name

        var currentColor = userLogged.activitiesColors[position%6]

        holder.layout.setBackgroundColor(currentColor)

    }

    override fun getItemCount(): Int {
        return activityList.size
    }

}
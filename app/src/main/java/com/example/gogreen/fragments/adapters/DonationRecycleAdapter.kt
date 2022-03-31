package com.example.gogreen.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gogreen.R
import com.example.gogreen.models.DonoHistory

class DonationRecycleAdapter(private val donationList: List<DonoHistory>, private val listener:OnItemClickListener) : RecyclerView.Adapter<DonationRecycleAdapter.MyViewHolder>(){

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var tvAmount: TextView = view.findViewById(R.id.tvDonoHAmount)
        var tvTime: TextView = view.findViewById(R.id.tvDonoHTime)

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donation_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRec = donationList[position]

        holder.tvAmount.text = String.format("%.2f", currentRec.amount)
        holder.tvTime.text = currentRec.time


    }

    override fun getItemCount(): Int {
        return donationList.size
    }

    }
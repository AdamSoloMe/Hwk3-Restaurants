package com.adamsolomon.hwk3_restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResturantAdapter(private var resturantList: List<Resturant>) :

    RecyclerView.Adapter<ResturantAdapter.MyViewHolder>() {

        private lateinit var listener: ResturantAdapterListener

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameView: TextView = itemView.findViewById(R.id.resturantItemName)
            val locationView: TextView = itemView.findViewById(R.id.resturantItemLocation)
            val ratingView: RatingBar = itemView.findViewById(R.id.restruantItemRatingsBar)


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurantview, parent, false)
            return MyViewHolder(view)


        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val resturant = resturantList[position]
            holder.nameView.text = resturant.name
            holder.locationView.text = resturant.location
            holder.ratingView.rating = resturant.rating!!?.toFloat()!!

        }

        override fun getItemCount(): Int {
            return resturantList.size

        }

        fun setData(list: List<Resturant>) {
            resturantList = list
            notifyDataSetChanged()
        }

        interface ResturantAdapterListener {
            fun onClick(position: Int)

        }

        fun setOnItemClickListener(_listener: ResturantAdapterListener) {
            listener = _listener
        }
    }

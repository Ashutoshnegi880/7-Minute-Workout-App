package com.example.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(val context: Context, val item:ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder> () {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val llHistoryMainItem = view.findViewById<LinearLayout>(R.id.ll_history_item_main)
        val tvItem = view.findViewById<TextView>(R.id.tvItem)
        val tvPosition = view.findViewById<TextView>(R.id.tvPosition)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.item_history_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val date: String = item.get(position)

        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        if (position % 2 == 0){
            holder.llHistoryMainItem.setBackgroundColor(
                Color.parseColor("#EBEBEB"))
        }else{
            holder.llHistoryMainItem.setBackgroundColor(
                ContextCompat.getColor(context, R.color.colorWhite))
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }
}
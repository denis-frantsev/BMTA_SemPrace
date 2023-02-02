package com.example.bmta_semprace.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmta_semprace.R
import com.example.bmta_semprace.models.Relapse

class RelapseAdapter(private var relapses: List<Relapse>) : RecyclerView.Adapter<RelapseAdapter.RelapseViewHolder>() {
    // inner class that represents the views for each item in the RecyclerView
    inner class RelapseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.txtDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelapseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.relapse_item_layout, parent, false)
        return RelapseViewHolder(view)
    }

    override fun getItemCount(): Int = relapses.size

    override fun onBindViewHolder(holder: RelapseViewHolder, position: Int) {
        val relapse = relapses[position]
        var displayText:String = if(relapse.reason.isNullOrEmpty())
            "Date: ${relapse.date}"
        else
            "Date: ${relapse.date}, Reason: ${relapse.reason}"
        holder.dateTextView.text = displayText
    }

    fun updateRelapseList(newRelapses: List<Relapse>) {
        relapses = newRelapses
        notifyDataSetChanged()
    }
}

package com.example.newcriminallist.screens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newcriminallist.R
import com.example.newcriminallist.data.Crime
import com.example.newcriminallist.databinding.ListItemCrimeBinding
import java.util.UUID

class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onItemClicked: (crimeId: UUID) -> Unit,
    private val onDeleteItem: (crimeId: UUID) -> Unit
)
    : RecyclerView.Adapter<CrimeListAdapter.CrimeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater,parent,false)

        return CrimeHolder(binding)
    }

    override fun getItemCount(): Int {
        return crimes.size
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]

        holder.bind(crime, onItemClicked, onDeleteItem)
    }

    class CrimeHolder(private val binding: ListItemCrimeBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(crime: Crime,
                 onItemClicked: (crimeID: UUID) -> Unit,
                 onDeleteItem: (crimeId: UUID) -> Unit
                 ){
            binding.apply {
                crimeTitle.text = crime.title
                crimeDate.text = crime.date.toString()

                deleteImg.setOnClickListener {
                   onDeleteItem(crime.id)
                }

                root.setOnClickListener {
                    onItemClicked(crime.id)
                }

                crimeSolved.visibility = if(crime.isSolved){
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }
}
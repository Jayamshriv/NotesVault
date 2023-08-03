package com.example.notevault.ui.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.databinding.ItemNotesBinding
import com.example.notevault.ui.Activities.EditNote

class NotesVaultHomeAdapter(val context: Context?, var notes: List<NotesVault>?) :
    RecyclerView.Adapter<NotesVaultHomeAdapter.NotesVaultHomeHolder>() {

    fun filtering(newListNotes: ArrayList<NotesVault>) {
        notes = newListNotes
        notifyDataSetChanged()
    }

    inner class NotesVaultHomeHolder(val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVaultHomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemNotesBinding = ItemNotesBinding.inflate(inflater, parent, false)
        return NotesVaultHomeHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesVaultHomeHolder, position: Int) {
        val note = notes!![position]
        holder.binding.titleTV.text = note.title
        holder.binding.descTV.text = note.desc
        holder.binding.dateTV.text = note.date

        when (note.priority) {
            "1" -> {
                holder.binding.priorityIV.setImageResource(R.drawable.low_priority_green)
            }
            "5" -> {
                holder.binding.priorityIV.setImageResource(R.drawable.med_priority_yellow)
            }
            "10" -> {
                holder.binding.priorityIV.setImageResource(R.drawable.high_priority_red)
            }
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditNote::class.java)
            intent.putExtra("noteID", note.id)
            intent.putExtra("noteTitle", note.title)
            intent.putExtra("noteDesc", note.desc)
            intent.putExtra("noteNote", note.note)
            intent.putExtra("notePriority", note.priority)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

}

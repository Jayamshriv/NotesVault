package com.example.notevault.ui.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.databinding.ItemNotesBinding
import com.example.notevault.ui.Activities.EditNote
import kotlin.properties.Delegates

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

        holder.itemView.setOnClickListener {

            if (note.password.isNotBlank()) {

                Log.v("#####", "Passord in note")
                passwordFromConfirmationDialog(holder.itemView.context, note) { ifPasswordCorrect ->
                    Log.v("#####", "ifpasswordCorrect $ifPasswordCorrect")

                    if (ifPasswordCorrect) {

                        Log.v("#####", "ifpasswordCorrect  True 1 ${ifPasswordCorrect}")

                        val intent = Intent(holder.itemView.context, EditNote::class.java)
                        intent.putExtra("noteID", note.id)
                        intent.putExtra("noteTitle", note.title)
                        intent.putExtra("noteDesc", note.desc)
                        intent.putExtra("noteNote", note.note)
                        intent.putExtra("notePriority", note.priority)
                        holder.itemView.context.startActivity(intent)

                    } else {
                        Log.v("#####", "ifpasswordCorrect  False  $ifPasswordCorrect")

                        Toast.makeText(context, "Password galat hai bawa", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {

                Log.v("#####", "No Password in note")

                val intent = Intent(holder.itemView.context, EditNote::class.java)
                intent.putExtra("noteID", note.id)
                intent.putExtra("noteTitle", note.title)
                intent.putExtra("noteDesc", note.desc)
                intent.putExtra("noteNote", note.note)
                intent.putExtra("notePriority", note.priority)
                holder.itemView.context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

    fun passwordFromConfirmationDialog(
        context: Context,
        noteWPassword: NotesVault,
        onResult: (Boolean) -> Unit
    ) {
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.password_confirmation_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        dialogView.findViewById<TextView>(R.id.okPswdConfBtn).setOnClickListener {
            val passwordET = dialogView.findViewById<EditText>(R.id.passwordFromDialog)
            val passwordFromDialog = passwordET.text.toString().trim()

            val isPasswordCorrect: Boolean = passwordFromDialog == noteWPassword.password
            onResult(isPasswordCorrect)

            alertDialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.cancelPswdConfBtn).setOnClickListener {
            alertDialog.dismiss()
        }
    }


}

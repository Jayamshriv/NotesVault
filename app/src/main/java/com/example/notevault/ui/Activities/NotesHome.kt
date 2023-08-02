package com.example.notevault.ui.Activities


import android.content.Intent
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notevault.Models.NotesVault
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityNotesHomeBinding
import com.example.notevault.ui.Adapter.NotesVaultHomeAdapter


class NotesHome : AppCompatActivity() {

    lateinit var binding: ActivityNotesHomeBinding
    val viewModel: NotesVaultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesHomeBinding.inflate(layoutInflater)

        binding.createBtn.setOnClickListener {
            val intent = Intent(this, CreateNote::class.java)
            startActivity(intent)
        }

       val notesVault = listOf(
           NotesVault(null,"adf","sdad","sdad","sdad","sdad","sdad"),
           NotesVault(null,"adf","sdad","sdad","sdad","sdad","sdad"),
           NotesVault(null,"adf","sdad","sdad","sdad","sdad","sdad"),
           NotesVault(null,"adf","sdad","sdad","sdad","sdad","sdad"),
           NotesVault(null,"adf","sdad","sdad","sdad","sdad","sdad"),
           NotesVault(null,"adf","sdad","sdad","sdad","sdad","sdad")
       )

        viewModel.getNotes().observe(this, { notes ->

            binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
            binding.allNotesRV.adapter = NotesVaultHomeAdapter(applicationContext, notes)

            for (note in notesVault){
                Log.d("Home","ID: ${note.id}, Title: ${note.title}, \n desc : ${note.desc}\n note : ${note.note}")
            }
        })


        NotesHome().onBackPressedDispatcher.addCallback(/* owner = */ this){
            finish()
        }
        setContentView(binding.root)


    }

}
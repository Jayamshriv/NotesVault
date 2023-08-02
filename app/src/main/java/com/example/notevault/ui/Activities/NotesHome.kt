package com.example.notevault.ui.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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

        viewModel.getNotes().observe(this, { notes ->

            for (note in notes){
                Log.d("Home","ID: ${note.id}, Title: ${note.title}, \n desc : ${note.desc}\n note : ${note.note}")
            }
            binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
            binding.allNotesRV.adapter = NotesVaultHomeAdapter(applicationContext, notes)

        })

        setContentView(binding.root)


    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}
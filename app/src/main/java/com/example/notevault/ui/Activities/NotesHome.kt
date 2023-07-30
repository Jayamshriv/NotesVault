package com.example.notevault.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notevault.databinding.ActivityNotesHomeBinding

class NotesHome : AppCompatActivity() {

    lateinit var binding: ActivityNotesHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createBtn.setOnClickListener {
            val intent = Intent(this,CreateNote::class.java)
            startActivity(intent)
        }

    }

}
package com.example.notevault.ui.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notevault.R
import com.example.notevault.databinding.ActivityCreateNoteBinding
import com.example.notevault.databinding.ActivityNotesHomeBinding

class CreateNote : AppCompatActivity() {

    lateinit var binding: ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
package com.example.notevault.ui.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable.Factory
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.notevault.R
import com.example.notevault.databinding.ActivityEditNoteBinding

class EditNote : AppCompatActivity() {


    lateinit var binding: ActivityEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)

        val title = intent.getStringExtra("noteTitle")
        val desc = intent.getStringExtra("noteDesc")
        val note = intent.getStringExtra("noteNote")
        val priority = intent.getStringExtra("notePriority")

        when(priority){
            "1"->{
                binding.lowPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.low_priority_green
                    )
                )
                binding.highPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.priority_btn_bg
                    )
                )
                binding.medPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.priority_btn_bg
                    )
                )
            }
            "5"->{
                binding.medPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.med_priority_yellow
                    )
                )
                binding.highPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.priority_btn_bg
                    )
                )
                binding.lowPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.priority_btn_bg
                    )
                )
            }
            "10"->{
                binding.highPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.high_priority_red
                    )
                )
                binding.medPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.priority_btn_bg
                    )
                )
                binding.lowPriority.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.priority_btn_bg
                    )
                )
            }
        }

        binding.titleET.text = Editable.Factory.getInstance().newEditable(title)
        binding.desc.text = Editable.Factory.getInstance().newEditable(desc)
        binding.note.text = Editable.Factory.getInstance().newEditable(note)


        setContentView(binding.root)


    }

}
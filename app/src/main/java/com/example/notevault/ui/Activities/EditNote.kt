package com.example.notevault.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable.Factory
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityEditNoteBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditNote : AppCompatActivity() {

    val viewModel : NotesVaultViewModel by viewModels()
    lateinit var priority : String
    lateinit var title : String
    lateinit var desc : String
    lateinit var note : String
    var id : Int? = null
    var notesVault = NotesVault(null,"","","","","","")
    lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)

        id = intent.getIntExtra("noteID", -1)
        title = intent.getStringExtra("noteTitle")!!
        desc = intent.getStringExtra("noteDesc")!!
        note = intent.getStringExtra("noteNote")!!
        priority = intent.getStringExtra("notePriority")!!


        binding.titleET.text = Editable.Factory.getInstance().newEditable(title)
        binding.desc.text = Editable.Factory.getInstance().newEditable(desc)
        binding.note.text = Editable.Factory.getInstance().newEditable(note)

        priorityCheck(priority)

        binding.highPriority.setOnClickListener {
            priority = "10"
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

        binding.medPriority.setOnClickListener {
            priority = "5"
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

        binding.lowPriority.setOnClickListener {
            priority = "1"
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

        binding.checkBtn.setOnClickListener {
            editNotes()
            finish()
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.deleteNotes(id!!)
            startActivity(Intent(applicationContext,NotesHome::class.java))
        }


        binding.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Title : ${title}\nDescription : ${desc}\nNote :${note}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

        setContentView(binding.root)

    }


    private fun editNotes(){
        notesVault.id = id
        notesVault.title = binding.titleET.text.toString()
        notesVault.desc = binding.desc.text.toString()
        notesVault.note = binding.note.text.toString()
        notesVault.priority = priority

        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        notesVault.date = date.toString()
        viewModel.updateNotes(notesVault)
    }


    fun priorityCheck(priority : String) {
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

    }

}
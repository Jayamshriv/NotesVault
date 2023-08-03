package com.example.notevault.ui.Activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.Repository.NotesVaultRepository
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityCreateNoteBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class CreateNote : AppCompatActivity() {

    var notesVault: NotesVault = NotesVault(null, "", "", "", "", "", "")
    lateinit var binding: ActivityCreateNoteBinding
    val viewModel: NotesVaultViewModel by viewModels()
    var priority = "1"


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Do something when the back button is pressed.
            // For example, you could pop the current fragment off the back stack.
            if (supportFragmentManager.backStackEntryCount > 0) {
//                supportFragmentManager.popBackStack()
                android.os.Process.killProcess(android.os.Process.myPid())
                Toast.makeText(this@CreateNote, "Create note to Finish()", Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            createNotes()
        }

        binding.shareBtn.setOnClickListener {
            createNotes()

            if (notesVault.title.isNotBlank()) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Title : ${notesVault.title}\nDescription : ${notesVault.desc}\nNote :${notesVault.note}"
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                finish()
            } else {
                Toast.makeText(this, "Title is Empty Again, BKL !!!", Toast.LENGTH_SHORT).show()
            }
        }


    }


    fun createNotes() {
        notesVault.title = binding.titleET.text.toString()
        notesVault.desc = binding.desc.text.toString()
        notesVault.note = binding.note.text.toString()
        notesVault.priority = priority
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        notesVault.date = date.toString()

        if (notesVault.title.isNotBlank()) {
            viewModel.insertNotes(notesVault)

            finish()

        } else {
            Toast.makeText(this, "Title is Empty, BKL !!!", Toast.LENGTH_SHORT).show()
        }
    }
}
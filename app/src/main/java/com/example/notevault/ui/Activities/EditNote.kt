package com.example.notevault.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityEditNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditNote : AppCompatActivity() {

    val viewModel: NotesVaultViewModel by viewModels()
    lateinit var priority: String
    lateinit var title: String
    lateinit var desc: String
    lateinit var note: String
    var id: Int? = null
    var notesVault = NotesVault(id, "", "", "", "", "", "")
    lateinit var binding: ActivityEditNoteBinding

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Do something when the back button is pressed.
            // For example, you could pop the current fragment off the back stack.
            if (supportFragmentManager.backStackEntryCount > 0) {
//                supportFragmentManager.popBackStack()
                startActivity(Intent(applicationContext, NotesHome::class.java))
                Toast.makeText(this@EditNote, "Edit note to Create Note", Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }
    }


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

            val bottomSheetDialog = BottomSheetDialog(this@EditNote)
            bottomSheetDialog.setContentView(R.layout.delete_note_dialog)

            val textViewYes = bottomSheetDialog.findViewById<TextView>(R.id.yesDltBtn)
            val textViewNo = bottomSheetDialog.findViewById<TextView>(R.id.noDltBtn)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(id!!)
                bottomSheetDialog.dismiss()
                Toast.makeText(this,"Notes Deleted",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, NotesHome::class.java))
            }

            textViewNo?.setOnClickListener{
                Toast.makeText(this,"Nhi Kr Raha Jaaa",Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }


        binding.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Title : ${title}\nDescription : ${desc}\nNote :${note}"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }


        binding.homeBackBtn.setOnClickListener {
            startActivity(Intent(applicationContext, NotesHome::class.java))
            Toast.makeText(this@EditNote, "Edit note to Create Note", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        setContentView(binding.root)

    }


    private fun editNotes() {
        notesVault.id = id
        notesVault.title = binding.titleET.text.toString()
        notesVault.desc = binding.desc.text.toString()
        notesVault.note = binding.note.text.toString()
        notesVault.priority = priority

        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        notesVault.date = date.toString()
        viewModel.updateNotes(notesVault)
    }


    fun priorityCheck(priority: String) {
        when (priority) {
            "1" -> {
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
            "5" -> {
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
            "10" -> {
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
package com.example.notevault.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityCreateNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateNote : AppCompatActivity() {

    var notesVault: NotesVault = NotesVault(null, "", "", "", "", "", "")
    lateinit var binding: ActivityCreateNoteBinding
    val viewModel: NotesVaultViewModel by viewModels()
    var priority = "1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })


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


        binding.homeBackBtn.setOnClickListener {
            finish()
        }

        binding.checkBtn.setOnClickListener {

            val titleText = binding.titleET.text.toString().trim()
            if (titleText.isEmpty()) {
                binding.titleET.error = "Title can't be Empty"
//                Toast.makeText(this, "Title is Empty, BKL !!!", Toast.LENGTH_SHORT).show()
            } else {
                showPswdConfirm()
            }
        }

        binding.shareBtn.setOnClickListener {
            val titleText = binding.titleET.text.toString().trim()
            if (titleText.isEmpty()) {
                binding.titleET.error = "Title can't be Empty"
//                Toast.makeText(this, "Title is Empty, BKL !!!", Toast.LENGTH_SHORT).show()
            } else {
                showPswdConfirm()
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
            }
        }


    }


    fun createNote(passwd: String) {
        notesVault.title = binding.titleET.text.toString()
        notesVault.desc = binding.desc.text.toString()
        notesVault.note = binding.note.text.toString()
        notesVault.priority = priority

        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        notesVault.date = date.toString()
        notesVault.password = passwd

        viewModel.insertNotes(notesVault)
        finish()
    }

    fun createNotes() {
        notesVault.title = binding.titleET.text.toString()
        notesVault.desc = binding.desc.text.toString()
        notesVault.note = binding.note.text.toString()
        notesVault.priority = priority

        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        notesVault.date = date.toString()

        viewModel.insertNotes(notesVault)
        finish()

    }

    fun showPswdConfirm() {

        // show password yes or no
        val bottomSheet = BottomSheetDialog(this@CreateNote)
        bottomSheet.setContentView(R.layout.delete_note_dialog)

        val textDialog = bottomSheet.findViewById<TextView>(R.id.textDialog)
        textDialog?.text = "Want to make this Note\nPassword Protected ?"
        val yesTV = bottomSheet.findViewById<TextView>(R.id.yesDltBtn)
        val noTV = bottomSheet.findViewById<TextView>(R.id.noDltBtn)

        Log.v("#####", "Bottomsheet notes se baad ")

        yesTV?.setOnClickListener {
            Log.v("#####", "YesTv 1 ke andar ")
            showEnterPswd()
            bottomSheet.dismiss()
        }
        noTV?.setOnClickListener {

            val passwordIV = findViewById<ImageView>(R.id.hasPasswordIV)
            if(passwordIV == null) {
                Log.v("####", "Image view null")
            }else {
                passwordIV.visibility = View.INVISIBLE
                createNotes()
            }


            Log.v("#####", "NoTv 1 ke andar ")
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }


    fun showEnterPswd() {

        Log.v("#####", "YesTv 2 ke andar ")

        // if yes then shows password dialog to enter password
        val bottomSheetDialog = BottomSheetDialog(this@CreateNote)
        bottomSheetDialog.setContentView(R.layout.password_dialog)
//        Toast.makeText(this@CreateNote, "Yes clicked", Toast.LENGTH_SHORT).show()

        val okTV = bottomSheetDialog.findViewById<TextView>(R.id.okPswdBtn)
        val cancelTV = bottomSheetDialog.findViewById<TextView>(R.id.cancelPswdBtn)

        okTV?.setOnClickListener {
            val pswdET = bottomSheetDialog.findViewById<EditText>(R.id.password)

            val pswd = pswdET?.text.toString().trim()

            Log.v("#####", "okTV ke andar ${pswd},${pswd.length} ")

             createNote(pswd)
            bottomSheetDialog.dismiss()
        }

        cancelTV?.setOnClickListener {

            val passwordIV = findViewById<ImageView>(R.id.hasPasswordIV)
            if(passwordIV == null) {
                Log.v("####", "Image view null")
            }else {
                passwordIV.visibility = View.INVISIBLE
                createNotes()
            }
//            Toast.makeText(this@CreateNote, "Cancel clicked", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}

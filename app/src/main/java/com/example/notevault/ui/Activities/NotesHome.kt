package com.example.notevault.ui.Activities


import android.content.Intent
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityNotesHomeBinding
import com.example.notevault.ui.Adapter.NotesVaultHomeAdapter


class NotesHome : AppCompatActivity() {

    lateinit var binding: ActivityNotesHomeBinding
    val viewModel: NotesVaultViewModel by viewModels()


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Do something when the back button is pressed.
            // For example, you could pop the current fragment off the back stack.
            if (supportFragmentManager.backStackEntryCount > 0) {
//                supportFragmentManager.popBackStack()
                android.os.Process.killProcess(android.os.Process.myPid())
                Toast.makeText(this@NotesHome, "Create note to Finish()", Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesHomeBinding.inflate(layoutInflater)

        binding.createBtn.setOnClickListener {
            val intent = Intent(this, CreateNote::class.java)
            startActivity(intent)
        }

        val notesVault = listOf(
            NotesVault(null, "adf", "sdad", "sdad", "sdad", "sdad", "sdad"),
            NotesVault(null, "adf", "sdad", "sdad", "sdad", "sdad", "sdad"),
            NotesVault(null, "adf", "sdad", "sdad", "sdad", "sdad", "sdad"),
            NotesVault(null, "adf", "sdad", "sdad", "sdad", "sdad", "sdad"),
            NotesVault(null, "adf", "sdad", "sdad", "sdad", "sdad", "sdad"),
            NotesVault(null, "adf", "sdad", "sdad", "sdad", "sdad", "sdad")
        )

        viewModel.getNotes().observe(this) { notes ->

            binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
            binding.allNotesRV.adapter = NotesVaultHomeAdapter(applicationContext, notes)

        }

        binding.filterImage.setOnClickListener {
            showPopupMenu(it)
        }


        setContentView(binding.root)


    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.filter_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.allNotes -> {
                    viewModel.getNotes().observe(this) { notes ->

                        binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
                        binding.allNotesRV.adapter =
                            NotesVaultHomeAdapter(applicationContext, notes)
                        NotesVaultHomeAdapter(applicationContext, notes)
                    }

                    true
                }


                R.id.date -> {
                    viewModel.dateFilter().observe(this) { notes ->

                        binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
                        binding.allNotesRV.adapter =
                            NotesVaultHomeAdapter(applicationContext, notes)
                        NotesVaultHomeAdapter(applicationContext, notes)
                    }
                    true

                }


                R.id.highMenuPriority -> {
                    viewModel.highPriorityFilter().observe(this) { notes ->

                        binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
                        binding.allNotesRV.adapter =
                            NotesVaultHomeAdapter(applicationContext, notes)
                        NotesVaultHomeAdapter(applicationContext, notes)
                    }
                    true
                }

                R.id.medMenuPriority -> {

                    viewModel.medPriorityFilter().observe(this) { notes ->

                        binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
                        binding.allNotesRV.adapter =
                            NotesVaultHomeAdapter(applicationContext, notes)
                        NotesVaultHomeAdapter(applicationContext, notes)
                    }
                    true
                }

                R.id.lowMenuPriority -> {

                    viewModel.lowPriorityFilter().observe(this) { notes ->

                        binding.allNotesRV.layoutManager = GridLayoutManager(applicationContext, 2)
                        binding.allNotesRV.adapter =
                            NotesVaultHomeAdapter(applicationContext, notes)
                        NotesVaultHomeAdapter(applicationContext, notes)
                    }
                    true
                }


                else -> {
                    false
                }
            }
        }


        popupMenu.show()
    }

}
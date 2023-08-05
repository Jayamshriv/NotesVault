package com.example.notevault.ui.Activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notevault.Models.NotesVault
import com.example.notevault.R
import com.example.notevault.ViewModel.NotesVaultViewModel
import com.example.notevault.databinding.ActivityNotesHomeBinding
import com.example.notevault.ui.Adapter.NotesVaultHomeAdapter
import kotlin.system.exitProcess


class NotesHome : AppCompatActivity() {

    lateinit var binding: ActivityNotesHomeBinding
    val viewModel: NotesVaultViewModel by viewModels()
    var oldNotesList = arrayListOf<NotesVault>()
    lateinit var adapter: NotesVaultHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            private var backPressedOnce = false
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    // Close the app.
                    finishAffinity()
                } else {
                    backPressedOnce = true
                    Toast.makeText(applicationContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding = ActivityNotesHomeBinding.inflate(layoutInflater)
        //adapter
        binding.allNotesRV.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        //create Button
        binding.createBtn.setOnClickListener {
            val intent = Intent(this, CreateNote::class.java)
            startActivity(intent)
        }

        // get notes
        viewModel.getNotes().observe(this) { notes ->
            oldNotesList = notes as ArrayList<NotesVault>
            adapter = NotesVaultHomeAdapter(applicationContext, notes)
            binding.allNotesRV.adapter = adapter
        }

        // popup menu
        binding.filterImage.setOnClickListener {
            showPopupMenu(it)
        }

        //search bar
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesSearching(newText)
                return true
            }
        })

        setContentView(binding.root)

    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.filter_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.allNotes -> {
                    viewModel.getNotes().observe(this) { notes ->
                        oldNotesList = notes as ArrayList<NotesVault>
                        adapter = NotesVaultHomeAdapter(applicationContext, notes)
                        binding.allNotesRV.adapter = adapter
                    }
                    true
                }


                R.id.date -> {
                    viewModel.dateFilter().observe(this) { notes ->
                        oldNotesList = notes as ArrayList<NotesVault>
                        adapter = NotesVaultHomeAdapter(applicationContext, notes)
                        binding.allNotesRV.adapter = adapter
                    }
                    true
                }


                R.id.highMenuPriority -> {
                    viewModel.highPriorityFilter().observe(this) { notes ->
                        oldNotesList = notes as ArrayList<NotesVault>
                        adapter = NotesVaultHomeAdapter(applicationContext, notes)
                        binding.allNotesRV.adapter = adapter
                    }
                    true
                }

                R.id.medMenuPriority -> {
                    viewModel.medPriorityFilter().observe(this) { notes ->
                        oldNotesList = notes as ArrayList<NotesVault>
                        adapter = NotesVaultHomeAdapter(applicationContext, notes)
                        binding.allNotesRV.adapter = adapter
                    }
                    true
                }

                R.id.lowMenuPriority -> {
                    viewModel.lowPriorityFilter().observe(this) { notes ->
                        oldNotesList = notes as ArrayList<NotesVault>
                        adapter = NotesVaultHomeAdapter(applicationContext, notes)
                        binding.allNotesRV.adapter = adapter
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

    private fun notesSearching(newText1: String?) {

        val newListNotes = arrayListOf<NotesVault>()
        val newText = newText1?.lowercase()

        for (i in oldNotesList) {
            if (i.title.lowercase().contains(newText!!) ||
                i.desc.lowercase().contains(newText!!) ||
                i.note.lowercase().contains(newText!!)
            ) {
                newListNotes.add(i)
            }
        }
        adapter.filtering(newListNotes)

    }


}
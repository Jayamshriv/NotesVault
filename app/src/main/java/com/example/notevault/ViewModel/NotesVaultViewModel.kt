package com.example.notevault.ViewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notevault.Database.NotesVaultDatabase
import com.example.notevault.Models.NotesVault
import com.example.notevault.Repository.NotesVaultRepository

class NotesVaultViewModel(application: Application) :AndroidViewModel(application) {


    private val repository : NotesVaultRepository

    init{
        val dao = NotesVaultDatabase.getDatabase(application)
            .notesVaultDao()
        repository = NotesVaultRepository(dao)
    }

    fun getNotes(): LiveData<List<NotesVault>>{
        return repository.getNotes()
    }

    fun insertNotes(notesVault: NotesVault){
        repository.insertNotes(notesVault)
        Toast.makeText(getApplication(),"Notes Created",Toast.LENGTH_SHORT).show()
        Log.d("Insert ","VIEW MODEL")
    }

    fun deleteNotes(id : Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notesVault: NotesVault){
        repository.updateNotes(notesVault)
    }

}
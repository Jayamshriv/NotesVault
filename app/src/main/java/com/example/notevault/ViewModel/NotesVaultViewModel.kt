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
    }

    fun deleteNotes(id : Int){
        Toast.makeText(getApplication(),"Notes Deleted",Toast.LENGTH_SHORT).show()
        repository.deleteNotes(id)
    }

    fun updateNotes(notesVault: NotesVault){
        repository.updateNotes(notesVault)
        Toast.makeText(getApplication(),"Notes Updated",Toast.LENGTH_SHORT).show()
    }


    fun highPriorityFilter() : LiveData<List<NotesVault>>{
        Toast.makeText(getApplication(),"Filtered for High Priority",Toast.LENGTH_SHORT).show()
        return repository.highPriorityFilter()
    }

    fun medPriorityFilter() : LiveData<List<NotesVault>>{
        Toast.makeText(getApplication(),"Filtered for Med Priority",Toast.LENGTH_SHORT).show()
        return repository.medPriorityFilter()
    }

    fun lowPriorityFilter(): LiveData<List<NotesVault>>{
        Toast.makeText(getApplication(),"Filtered for Low Priority",Toast.LENGTH_SHORT).show()
        return repository.lowPriorityFilter()
    }

    fun dateFilter(): LiveData<List<NotesVault>>{
        Toast.makeText(getApplication(),"Filtered for Priority",Toast.LENGTH_SHORT).show()
        return repository.dateFilter()
    }

}
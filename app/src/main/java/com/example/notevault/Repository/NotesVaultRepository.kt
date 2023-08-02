package com.example.notevault.Repository

import androidx.lifecycle.LiveData
import com.example.notevault.DAO.NotesVaultDao
import com.example.notevault.Models.NotesVault

class NotesVaultRepository(private val dao: NotesVaultDao) {

    fun getNotes() : LiveData<List<NotesVault>>{
        return dao.getNotes()
    }

    fun insertNotes(notesVault: NotesVault){
        dao.insertNotes(notesVault)
    }

    fun deleteNotes(id: Int){
        dao.deleteNotes(id)
    }

    fun updateNotes(notesVault: NotesVault){
        dao.updateNotes(notesVault)
    }

}
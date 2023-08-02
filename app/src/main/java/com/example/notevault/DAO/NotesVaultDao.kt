package com.example.notevault.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notevault.Models.NotesVault

@Dao
interface NotesVaultDao {

    @Query("SELECT * FROM NotesVault")
    fun getNotes() : LiveData<List<NotesVault>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notesVault: NotesVault)

    @Query("DELETE FROM NotesVault WHERE id=:id")
    fun deleteNotes(id:Int)

    @Update
    fun updateNotes(notesVault: NotesVault)
}
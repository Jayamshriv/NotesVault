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

    @Query("SELECT * FROM NotesVault WHERE priority = 10")
    fun getHighPriority() : LiveData<List<NotesVault>>

    @Query("SELECT * FROM NotesVault WHERE priority = 5")
    fun getMedPriority() : LiveData<List<NotesVault>>

    @Query("SELECT * FROM NotesVault WHERE priority = 1")
    fun getLowPriority() : LiveData<List<NotesVault>>

    @Query("SELECT * FROM NotesVault ORDER BY date DESC")
    fun dateFilter() : LiveData<List<NotesVault>>

}
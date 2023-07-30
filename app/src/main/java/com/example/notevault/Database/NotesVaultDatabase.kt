package com.example.notevault.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.notevault.DAO.NotesVaultDao
import com.example.notevault.Models.NotesVault
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [NotesVault::class], version = 1)
abstract class NotesVaultDatabase : RoomDatabase() {

    abstract fun notesVaultDao(): NotesVaultDao


    companion object{

        @Volatile
        private var DB_INSTANCE : NotesVaultDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context) : NotesVaultDatabase{

            val dbins = DB_INSTANCE
            if(dbins!=null) {
                return dbins
            }
            synchronized(this){
                    val dbInstance = Room.databaseBuilder(
                        context,
                        NotesVaultDatabase::class.java,
                        "NotesVault"
                    ).build()
                    DB_INSTANCE = dbInstance
                }
            return DB_INSTANCE!!

        }

    }

}
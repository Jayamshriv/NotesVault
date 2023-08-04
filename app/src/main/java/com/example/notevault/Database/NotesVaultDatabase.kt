package com.example.notevault.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.example.notevault.DAO.NotesVaultDao
import com.example.notevault.Models.NotesVault
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [NotesVault::class], version = 1, exportSchema = false)
abstract class NotesVaultDatabase : RoomDatabase() {

    abstract fun notesVaultDao(): NotesVaultDao


    companion object {

        @Volatile
        private var DB_INSTANCE: NotesVaultDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): NotesVaultDatabase {

            return DB_INSTANCE ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesVaultDatabase::class.java,
                    "NotesVault"
                )
                    .allowMainThreadQueries()
                    .build()
                DB_INSTANCE = dbInstance
                dbInstance
            }
        }
    }
}
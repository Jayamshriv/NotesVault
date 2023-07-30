package com.example.notevault.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity
data class NotesVault(
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val title: String,
    val desc : String,
    val note : String,
    val priority : String,
    val date: String,
    val password : String
)

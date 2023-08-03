package com.example.notevault.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.Date


@Entity
data class NotesVault(

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    var title: String,
    var desc : String,
    var note : String,
    var priority : String,
    var date: String,
    var password : String
)

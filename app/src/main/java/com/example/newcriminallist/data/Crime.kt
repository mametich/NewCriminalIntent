package com.example.newcriminallist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "crime")
data class Crime(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val date: Date,
    val isSolved: Boolean
)
package com.nmp90.mysimplediary.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(@PrimaryKey(autoGenerate = true) var id: Long?,
                @ColumnInfo(name = "text") var text: String,
                @ColumnInfo(name = "date") var date: Date) {
    constructor(): this(null,"", Date())
}

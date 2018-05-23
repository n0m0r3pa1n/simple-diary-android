package com.nmp90.mysimplediary.notes

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(@PrimaryKey(autoGenerate = true) var id: Long?,
                @ColumnInfo(name = "text") var text: String,
                @ColumnInfo(name = "date") var date: Date) {
    constructor(): this(null,"", Date())
}
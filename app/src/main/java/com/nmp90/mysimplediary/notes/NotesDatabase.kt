package com.nmp90.mysimplediary.notes

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.nmp90.mysimplediary.utils.db.DateConverter

@Database(entities = arrayOf(Note::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDataDao(): NotesDataDao

    companion object {
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase? {
            if (INSTANCE == null) {
                synchronized(NotesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase::class.java, "notes.db")
                            .build()

//                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
//                            NotesDatabase::class.java)
//                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
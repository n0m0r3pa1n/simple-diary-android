package com.nmp90.mysimplediary.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nmp90.mysimplediary.utils.db.DateConverter

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDataDao(): NotesDataDao

    companion object {
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase? {
            if (INSTANCE == null) {
                synchronized(NotesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
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

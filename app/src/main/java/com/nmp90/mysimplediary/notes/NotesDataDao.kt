package com.nmp90.mysimplediary.notes

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NotesDataDao {

    @Query("SELECT * from notes WHERE date BETWEEN :start_time AND :end_time ORDER BY date DESC")
    fun getAll(start_time: Long, end_time: Long):  DataSource.Factory<Int, Note>

    @Query("SELECT COUNT(*) from notes WHERE date BETWEEN :start_time AND :end_time")
    fun notesCount(start_time: Long, end_time: Long): Int

    @Insert(onConflict = REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(vararg notes: Note)

}

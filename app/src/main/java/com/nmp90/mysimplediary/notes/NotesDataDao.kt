package com.nmp90.mysimplediary.notes

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface NotesDataDao {

    @Query("SELECT * from notes ORDER BY date DESC")
    fun getAll(): Flowable<List<Note>>

    @Query("SELECT COUNT(*) from notes WHERE date BETWEEN :start_time AND :end_time")
    fun notesCount(start_time: Long, end_time: Long): Int

    @Insert(onConflict = REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(vararg notes: Note)

}
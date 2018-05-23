package com.nmp90.mysimplediary.notes

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface NotesDataDao {

    @Query("SELECT * from notes")
    fun getAll(): Flowable<List<Note>>

    @Insert(onConflict = REPLACE)
    fun insert(note: Note)

}
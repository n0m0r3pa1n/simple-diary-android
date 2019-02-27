package com.nmp90.mysimplediary.notes

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface NotesRepository {
    fun getNotes(startDate: Date, endDate: Date): LiveData<PagedList<Note>>

    fun saveNote(id: Long?, text: String, date: Date): Completable

    fun hasNotes(date: Date): Single<Boolean>

    fun deleteNote(note: Note): Completable
}

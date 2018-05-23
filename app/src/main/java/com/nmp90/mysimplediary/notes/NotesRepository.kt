package com.nmp90.mysimplediary.notes

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface NotesRepository {
    fun getNotes(startDate: Date, endDate: Date) : Flowable<List<Note>>

    fun saveNote(id: Long?, text: String, date: Date) : Completable

    fun hasNotes(date: Date) : Single<Boolean>
}
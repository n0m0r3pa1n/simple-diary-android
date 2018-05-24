package com.nmp90.mysimplediary

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import com.nmp90.mysimplediary.notes.DbNotesRepository
import com.nmp90.mysimplediary.notes.Note
import com.nmp90.mysimplediary.notes.NotesRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class DiaryViewModel : ViewModel() {
    val notesRepository: NotesRepository;

    init {
        notesRepository = DbNotesRepository(MySimpleDiaryApp.instance)
    }

    fun getNotes(): LiveData<List<Note>> {
        val notes = notesRepository.getNotes(Date(), Date())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        return LiveDataReactiveStreams.fromPublisher(notes)
    }

    fun hasNotesForToday() : Single<Boolean> {
        return notesRepository.hasNotes(Date())
    }

    fun saveNote(id: Long?, text: String, date: Date): Completable {
        return notesRepository.saveNote(id, text, date)
    }

    fun deleteNote(note: Note): Completable {
        return notesRepository.deleteNote(note)
    }
}
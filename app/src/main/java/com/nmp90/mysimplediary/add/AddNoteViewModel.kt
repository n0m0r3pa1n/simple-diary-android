package com.nmp90.mysimplediary.add

import androidx.lifecycle.ViewModel
import com.nmp90.mysimplediary.MySimpleDiaryApp
import com.nmp90.mysimplediary.notes.DbNotesRepository
import com.nmp90.mysimplediary.notes.NotesRepository
import io.reactivex.Completable
import java.util.*


class AddNoteViewModel : ViewModel() {
    val notesRepository: NotesRepository;

    init {
        notesRepository = DbNotesRepository(MySimpleDiaryApp.instance)
    }

    fun saveNote(id: Long?, text: String, date: Date): Completable {
        return notesRepository.saveNote(id, text, date)
    }
}

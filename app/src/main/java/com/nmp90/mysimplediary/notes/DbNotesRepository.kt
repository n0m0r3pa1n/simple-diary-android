package com.nmp90.mysimplediary.notes

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

class DbNotesRepository(context: Context) : NotesRepository {
    val db: NotesDatabase?

    init {
        db = NotesDatabase.getInstance(context)
    }

    override fun getNotes(startDate: Date, endDate: Date): Flowable<List<Note>> {
        return db?.notesDataDao()?.getAll()!!
    }

    override fun hasNotes(date: Date): Single<Boolean> {
        return Single.fromCallable({
            val startOfDay = Calendar.getInstance()
            val cal = Calendar.getInstance()
            cal.time = date
            startOfDay.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH))
            startOfDay.set(Calendar.MONTH, cal.get(Calendar.MONTH))
            startOfDay.set(Calendar.YEAR, cal.get(Calendar.YEAR))
            startOfDay.set(Calendar.MINUTE, 0)
            startOfDay.set(Calendar.HOUR_OF_DAY, 0)

            val startTime = startOfDay.timeInMillis
            startOfDay.add(Calendar.DAY_OF_MONTH, 1)
            val endTime = startOfDay.timeInMillis

            db?.notesDataDao()?.notesCount(startTime, endTime).let { it!! > 0 }
        })
    }

    override fun saveNote(id: Long?, text: String, date: Date): Completable {
        return Completable.fromAction({
            val note = Note(id, text, date)
            db?.notesDataDao()?.insert(note)
        })
    }

    override fun deleteNote(note: Note): Completable {
        return Completable.fromAction({
            db?.notesDataDao()?.delete(note)
        })
    }


}

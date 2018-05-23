package com.nmp90.mysimplediary.notes

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class DbNotesRepository(context: Context) : NotesRepository {
    val db: NotesDatabase?

    init {
        db = NotesDatabase.getInstance(context)
    }

    override fun getNotes(startDate: Date, endDate: Date): Flowable<List<Note>> {
//        return Maybe.timer(1, TimeUnit.SECONDS).flatMap {
//            val cal = Calendar.getInstance();
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//
//            val note = Note(1,"# Test \n\n djaslkdjsalkdj \n ## Test 2 \n\n test", cal.time);
//            val note2 = Note(2, "#testfd auopifu dasoifuaodifu aosid fuaoisdfuadsoifuodasi afdsupofasd", Date());
//
//            val notes = arrayListOf(note, note2)
//            Maybe.just(notes)
//        };

        return db?.notesDataDao()?.getAll()!!
    }

    override fun saveNote(id: Long?, text: String, date: Date): Completable {
        return Completable.fromAction({
            val note = Note(id, text, date)
            db?.notesDataDao()?.insert(note)
        })
    }

}

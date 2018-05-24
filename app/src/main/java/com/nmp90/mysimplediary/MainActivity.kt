package com.nmp90.mysimplediary

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import com.nmp90.mysimplediary.add.AddNoteActivity
import com.nmp90.mysimplediary.databinding.ActivityMainBinding
import com.nmp90.mysimplediary.notes.Note
import com.nmp90.mysimplediary.notes.NotesAdapter
import com.nmp90.mysimplediary.utils.extensions.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity(), NotesAdapter.NoteClickListener {

    lateinit var binding: ActivityMainBinding

    private val disposables = CompositeDisposable()

    private lateinit var diaryViewModel: DiaryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvDiary.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        val notesAdapter = NotesAdapter(this)
        binding.rvDiary.adapter = notesAdapter
        binding.fabAddNote.setOnClickListener({
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        })

        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel::class.java)
        diaryViewModel.getNotes()
                .observe(this, Observer {
                    notesAdapter.setNotes(it!!)
                })

        checkForNoteToday()
    }

    private fun checkForNoteToday() {
        val disposable = diaryViewModel.hasNotesForToday()
                .filter({ !it })
                .flatMapCompletable { diaryViewModel.saveNote(null, "", Date()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        disposables.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear();
    }

    override fun onSaveNote(id: Long?, text: String, date: Date) {

        val disposable = diaryViewModel.saveNote(id, text, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        disposables.add(disposable)

        binding.rvDiary.hideKeyboard()
    }

    override fun onDeleteNote(note: Note) {
        val disposable = diaryViewModel.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        disposables.add(disposable)
    }
}

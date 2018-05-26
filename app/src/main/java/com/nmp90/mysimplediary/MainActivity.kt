package com.nmp90.mysimplediary

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.nmp90.mysimplediary.add.AddNoteActivity
import com.nmp90.mysimplediary.databinding.ActivityMainBinding
import com.nmp90.mysimplediary.notes.Note
import com.nmp90.mysimplediary.notes.NotesAdapter
import com.nmp90.mysimplediary.notes.PeriodFilter
import com.nmp90.mysimplediary.settings.SettingsActivity
import com.nmp90.mysimplediary.utils.extensions.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainActivity : AppCompatActivity(), NotesAdapter.NoteClickListener {

    val SYSTEM_TRIGGERED_SELECT_COUNT = 1;

    lateinit var binding: ActivityMainBinding

    private val disposables = CompositeDisposable()
    private var spinnerSelectCount = 0
    private var periodFilter = PeriodFilter.WEEK

    private lateinit var notesAdapter: NotesAdapter
    private lateinit var diaryViewModel: DiaryViewModel

    val notesListObserver = Observer<List<Note>> {
        notesAdapter.setNotes(it!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvDiary.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        notesAdapter = NotesAdapter(this)
        binding.rvDiary.adapter = notesAdapter
        binding.fabAddNote.setOnClickListener({
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        })

        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel::class.java)

        getNotes(PeriodFilter.WEEK)

        checkForNoteToday()
    }

    private fun getNotes(periodFilter: PeriodFilter) {
        val notes = diaryViewModel.getNotes(periodFilter)
        if(notes.hasObservers()) {
            notes.removeObserver(notesListObserver)
        }

        notes.observe(this, notesListObserver)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val settings = menu.findItem(R.id.settings)
        settings.setOnMenuItemClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        val item = menu.findItem(R.id.spinner)
        val spinner = item.actionView as Spinner

        val adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (++spinnerSelectCount > SYSTEM_TRIGGERED_SELECT_COUNT) {
                    periodFilter = when (position) {
                        0 -> PeriodFilter.WEEK
                        1 -> PeriodFilter.MONTH
                        2 -> PeriodFilter.ALL_TIME
                        else -> {
                            PeriodFilter.WEEK
                        }
                    }

                    getNotes(periodFilter)

                }
            }

        }
        return true
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
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

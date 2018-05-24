package com.nmp90.mysimplediary.add

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nmp90.mysimplediary.R
import com.nmp90.mysimplediary.databinding.ActivityAddNoteBinding
import com.nmp90.mysimplediary.utils.extensions.toSimpleString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_note.*
import java.util.*


class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private var date: Date = Date()
    private val disposables = CompositeDisposable()

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)

        binding.content?.tvDate?.text = date.toSimpleString()
        binding.content?.tvDate?.setOnClickListener({ onDatePick() })
        binding.fabSave.setOnClickListener { view ->
            val text = binding.content?.etNoteText?.text.toString()
            val disposable = viewModel.saveNote(null, text, date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        finish()
                    })
            disposables.add(disposable)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear();
    }


    fun onDatePick() {
        val dialogFragment = DatePickerDialogFragment()
        dialogFragment.setListener(DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
            date = GregorianCalendar(i, i1, i2).time
            binding.content?.tvDate?.text = date.toSimpleString()
        })

        dialogFragment.show(supportFragmentManager, DatePickerDialogFragment.TAG)
    }


}

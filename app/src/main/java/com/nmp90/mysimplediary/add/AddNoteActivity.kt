package com.nmp90.mysimplediary.add

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nmp90.mysimplediary.R
import com.nmp90.mysimplediary.databinding.ActivityAddNoteBinding
import com.nmp90.mysimplediary.utils.extensions.toSimpleString
import kotlinx.android.synthetic.main.activity_add_note.*
import java.util.*


class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private var date: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        setSupportActionBar(toolbar)

        binding.content?.tvDate?.text = Date().toSimpleString()
        binding.content?.tvDate?.setOnClickListener({ onDatePick() })
        binding.fabSave.setOnClickListener { view ->
            print(binding.content?.etNoteText?.text.toString())
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    fun onDatePick() {
        val dialogFragment = DatePickerDialogFragment()
        dialogFragment.setListener(DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
            date = GregorianCalendar(i, i1, i2).time
            binding.content?.tvDate?.text = date?.toSimpleString()
        })

        dialogFragment.show(supportFragmentManager, DatePickerDialogFragment.TAG)
    }


}

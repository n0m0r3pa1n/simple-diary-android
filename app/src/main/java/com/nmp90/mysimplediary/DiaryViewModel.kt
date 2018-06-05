package com.nmp90.mysimplediary

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.nmp90.mysimplediary.notes.DbNotesRepository
import com.nmp90.mysimplediary.notes.Note
import com.nmp90.mysimplediary.notes.NotesRepository
import com.nmp90.mysimplediary.notes.PeriodFilter
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*


class DiaryViewModel : ViewModel() {
    val notesRepository: NotesRepository;
    val YEARS_COUNT_FOR_NOTES = 10

    init {
        notesRepository = DbNotesRepository(MySimpleDiaryApp.instance)
    }

    fun getNotes(period: PeriodFilter): LiveData<PagedList<Note>> {
        val startDate = getStartDateForPeriod(period)
        val endDate = getEndDateForPeriod(period)

        return notesRepository.getNotes(startDate, endDate)
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

    private fun getStartDateForPeriod(period: PeriodFilter): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)

        when (period) {
            PeriodFilter.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendar.add(Calendar.DAY_OF_WEEK, -1)
            }
            PeriodFilter.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.add(Calendar.DAY_OF_MONTH, -1)
            }
            PeriodFilter.ALL_TIME -> {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - YEARS_COUNT_FOR_NOTES)
            }
        }

        return calendar.time
    }

    private fun getEndDateForPeriod(period: PeriodFilter): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)

        when (period) {
            PeriodFilter.WEEK -> {
                val currentMonday = Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK)
                val nextSundayDate = currentMonday + 6
                calendar.add(Calendar.DAY_OF_MONTH, nextSundayDate)
            }
            PeriodFilter.MONTH ->
                calendar.set(Calendar.DAY_OF_MONTH,  calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            PeriodFilter.ALL_TIME -> {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + YEARS_COUNT_FOR_NOTES)
            }
        }

        return calendar.time
    }
}
package com.nmp90.mysimplediary.notes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nmp90.mysimplediary.databinding.ListItemNoteBinding
import com.nmp90.mysimplediary.utils.extensions.toSimpleString
import ru.noties.markwon.Markwon
import java.util.*

class NotesAdapter(private val noteClickListener: NoteClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val notesList: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemNoteBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)

        binding.tvNotePreview.setOnClickListener({
            binding.isInEdit = true
        })

        binding.btnSave.setOnClickListener(View.OnClickListener {
            if (viewHolder.adapterPosition == RecyclerView.NO_POSITION)
                return@OnClickListener

            val note = notesList[viewHolder.adapterPosition]
            noteClickListener.onSaveNote(note.id, binding.etNoteText.text.toString(), note.date)
        })

        binding.btnCancel.setOnClickListener(View.OnClickListener {
            if (viewHolder.adapterPosition == RecyclerView.NO_POSITION)
                return@OnClickListener

            val note = notesList[viewHolder.adapterPosition]
            binding.etNoteText.setText(note.text)
            binding.isInEdit = false
        })

        return viewHolder
    }

    fun addNotes(notes: List<Note>) {
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    fun setNotes(notes: List<Note>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindNotes(notesList[position], noteClickListener)
    }

    class ViewHolder(val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindNotes(note: Note, noteClickListener: NoteClickListener) {
            binding.date = note.date.toSimpleString()
            binding.isInEdit = false

            Markwon.setMarkdown(binding.tvNotePreview, note.text)

            binding.etNoteText.setText(note.text)
            binding.executePendingBindings()
        }
    }

    interface NoteClickListener {
        fun onSaveNote(id: Long?, text: String, date: Date)
    }
}
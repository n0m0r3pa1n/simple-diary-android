package com.nmp90.mysimplediary.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nmp90.mysimplediary.databinding.ListItemNoteBinding
import com.nmp90.mysimplediary.utils.extensions.toSimpleString
import ru.noties.markwon.Markwon
import java.util.*

class NotesAdapter(private val noteClickListener: NoteClickListener) : PagedListAdapter<Note, NotesAdapter.ViewHolder>(diffCallback) {

    private val notesList: MutableList<Note> = mutableListOf()

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemNoteBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)

        binding.tvNotePreview.setOnClickListener {
            openEditMode(binding)
        }

        binding.btnSave.setOnClickListener(View.OnClickListener {
            if (viewHolder.adapterPosition == RecyclerView.NO_POSITION)
                return@OnClickListener

            val note = notesList[viewHolder.adapterPosition]
            noteClickListener.onSaveNote(note.id, binding.etNoteText.text.toString(), note.date)

            binding.isInEdit = false
            binding.executePendingBindings()
        })

        binding.btnDelete.setOnClickListener(View.OnClickListener {
            if (viewHolder.adapterPosition == RecyclerView.NO_POSITION)
                return@OnClickListener

            val note = notesList[viewHolder.adapterPosition]
            noteClickListener.onDeleteNote(note)
        })

        binding.btnCancel.setOnClickListener(View.OnClickListener {
            if (viewHolder.adapterPosition == RecyclerView.NO_POSITION)
                return@OnClickListener

            val note = notesList[viewHolder.adapterPosition]
            binding.etNoteText.setText(note.text)
            binding.isInEdit = false
            binding.executePendingBindings()
        })

        return viewHolder
    }

    private fun openEditMode(binding: ListItemNoteBinding) {
        binding.isInEdit = true
        binding.executePendingBindings()
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
        holder.bindNotes(notesList[position])
    }

    class ViewHolder(val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindNotes(note: Note) {
            binding.note = note
            binding.date = note.date.toSimpleString()
            binding.isInEdit = false

            Markwon.setMarkdown(binding.tvNotePreview, note.text)
            binding.executePendingBindings()
        }
    }

    interface NoteClickListener {
        fun onDeleteNote(note: Note)

        fun onSaveNote(id: Long?, text: String, date: Date)
    }
}

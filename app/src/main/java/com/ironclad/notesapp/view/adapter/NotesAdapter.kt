package com.ironclad.notesapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ironclad.notesapp.R
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.databinding.ItemNotesBinding

class NotesAdapter(private val context: Context) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }) {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        ItemNotesBinding.bind(holder.itemView).apply {
            val note = getItem(position)

            textViewTitle.text = note.title
            textViewMessage.text = note.message
            textViewTime.text = note.updateAt

            when (note.label) {
                "Passwords" -> setColor(colorTag, R.color.violet)
                "Shopping List" -> setColor(colorTag, R.color.blue)
                "Task" -> setColor(colorTag, R.color.red)
                "Messages" -> setColor(colorTag, R.color.yellow)
                "List" -> setColor(colorTag, R.color.indigo)
                "Ideas" -> setColor(colorTag, R.color.green)
                "Other" -> setColor(colorTag, R.color.orange)
            }
        }
    }

    private fun setColor(colorTag: View, color: Int) {
        colorTag.background = ContextCompat.getDrawable(context, color)
    }
}
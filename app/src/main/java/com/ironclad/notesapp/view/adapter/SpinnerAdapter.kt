package com.ironclad.notesapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ironclad.notesapp.data.models.Label
import com.ironclad.notesapp.databinding.DropdownItemLabelBinding

class SpinnerAdapter(context: Context, listOfLabels: List<Label>) :
    ArrayAdapter<Label>(context, 0, listOfLabels) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = getItem(position)

        val binding = DropdownItemLabelBinding.inflate(LayoutInflater.from(context), parent, false)

        binding.imageViewColor.setImageResource(label?.color ?: 0)
        binding.textViewLabel.text = label?.name ?: ""

        return convertView ?: binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }
}
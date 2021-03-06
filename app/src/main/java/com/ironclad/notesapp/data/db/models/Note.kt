package com.ironclad.notesapp.data.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "message")
    var message: String,

    @ColumnInfo(name = "created_at")
    var createdAt: String,

    @ColumnInfo(name = "updated_at")
    var updateAt: String,

    @ColumnInfo(name = "priority")
    var priority: Int,

    @ColumnInfo(name = "label")
    var label: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
) : Parcelable
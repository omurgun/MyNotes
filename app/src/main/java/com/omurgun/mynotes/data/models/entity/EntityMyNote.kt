package com.omurgun.mynotes.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myNotes")
data class EntityMyNote (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = null,
    @ColumnInfo(name = "title")
    var title : String?,
    @ColumnInfo(name = "detail")
    var detail : String?,
    @ColumnInfo(name = "createdDate")
    var createdDate : String,
    @ColumnInfo(name = "updatedDate")
    var updatedDate : String,
    @ColumnInfo(name = "lastVisitDate")
    var lastVisitDate : String,
    @ColumnInfo(name = "deletedDate")
    var deletedDate : String? = null,
    @ColumnInfo(name = "isDeleted")
    var isDeleted : Boolean = false,


)
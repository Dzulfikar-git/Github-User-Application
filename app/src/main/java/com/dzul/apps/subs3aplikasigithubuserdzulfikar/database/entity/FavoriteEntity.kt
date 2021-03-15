package com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity (
    @PrimaryKey @ColumnInfo(name = "userLogin") val userLogin: String,
    @ColumnInfo(name = "userImage") val userImage: String?
)
package com.dzul.apps.subs3aplikasigithubuserdzulfikar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.dao.FavoriteDao
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity.FavoriteEntity

@Database(entities = arrayOf(FavoriteEntity::class), version = 1, exportSchema = false)
public abstract class FavoriteRoomDatabase : RoomDatabase() {

    abstract fun favDao(): FavoriteDao

    companion object {
        // Singleton to prevent multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null

        fun getFavDatabase(context: Context): FavoriteRoomDatabase {
            // If INSTANCE is NOT NULL, then return it.
            // Else, then create new database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRoomDatabase::class.java,
                    "favorite_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
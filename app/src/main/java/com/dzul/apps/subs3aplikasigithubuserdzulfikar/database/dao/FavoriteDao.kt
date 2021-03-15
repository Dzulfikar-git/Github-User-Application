package com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table ORDER BY userLogin ASC")
    fun getUsers(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table ORDER BY userLogin ASC")
    fun getUsersCursor() : Cursor

    @Query("SELECT * FROM favorite_table WHERE userLogin =:userLogin")
    fun getSelectUser(userLogin: String): Array<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fav: FavoriteEntity)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()

    @Query("DELETE FROM favorite_table WHERE userLogin =:userLogin")
    suspend fun deleteUser(userLogin: String)
}
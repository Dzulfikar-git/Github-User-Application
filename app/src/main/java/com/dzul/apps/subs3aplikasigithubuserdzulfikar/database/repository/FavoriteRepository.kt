package com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.repository

import androidx.annotation.WorkerThread
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.dao.FavoriteDao
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

// Pass DAO in constructor
class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allFavUsers: Flow<List<FavoriteEntity>> = favoriteDao.getUsers()

    @WorkerThread
    fun getSelectUser(userLogin: String) : Array<FavoriteEntity>{
        return favoriteDao.getSelectUser(userLogin)
    }

    @WorkerThread
    suspend fun insert(favoriteEntity: FavoriteEntity){
        favoriteDao.insert(favoriteEntity)
    }

    @WorkerThread
    suspend fun delete(userLogin: String){
        favoriteDao.deleteUser(userLogin)
    }

    @WorkerThread
    suspend fun deleteAll(){
        favoriteDao.deleteAll()
    }
}
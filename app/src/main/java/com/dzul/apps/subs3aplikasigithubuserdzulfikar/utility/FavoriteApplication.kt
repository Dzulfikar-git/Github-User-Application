package com.dzul.apps.subs3aplikasigithubuserdzulfikar.utility

import android.app.Application
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.FavoriteRoomDatabase
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.repository.FavoriteRepository

class FavoriteApplication : Application() {
    val database by lazy { FavoriteRoomDatabase.getFavDatabase(this) }
    val repository by lazy { FavoriteRepository(database.favDao()) }
}
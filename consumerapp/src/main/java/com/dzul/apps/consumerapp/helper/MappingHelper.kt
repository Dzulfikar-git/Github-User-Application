package com.dzul.apps.consumerapp.helper

import android.database.Cursor
import com.dzul.apps.consumerapp.data.FavoriteEntity

object MappingHelper {
    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteEntity>{
        val favList = ArrayList<FavoriteEntity>()

        favCursor?.apply {
            while(moveToNext()){
                val userLogin = getString(getColumnIndexOrThrow("userLogin"))
                val userImage = getString(getColumnIndexOrThrow("userImage"))
                favList.add(FavoriteEntity(userLogin, userImage))
            }
        }

        return favList
    }
}
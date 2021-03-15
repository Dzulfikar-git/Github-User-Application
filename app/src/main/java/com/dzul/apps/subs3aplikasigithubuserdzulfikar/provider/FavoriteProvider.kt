package com.dzul.apps.subs3aplikasigithubuserdzulfikar.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.FavoriteRoomDatabase
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.dao.FavoriteDao

class FavoriteProvider : ContentProvider() {

    companion object {
        val tblFavorites = "FavoriteUsers"
        val PROVIDER_NAME = "com.dzul.apps.subs3aplikasigithubuserdzulfikar.favoriteUsers" // Authority
        val uriString = "content://$PROVIDER_NAME/$tblFavorites"
        val CONTENT_URI = Uri.parse(uriString)

        val FAVORITES_ALL = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        uriMatcher.addURI(PROVIDER_NAME, tblFavorites, FAVORITES_ALL)
    }

    lateinit var favoriteDao: FavoriteDao

    override fun onCreate(): Boolean {
        if(context != null){
            val favoriteDatabase = FavoriteRoomDatabase.getFavDatabase(context!!)
            favoriteDao = favoriteDatabase.favDao()
        }
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }



    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        when(uriMatcher.match(uri)){
            FAVORITES_ALL -> {
                return favoriteDao.getUsersCursor()
            }
        }
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}

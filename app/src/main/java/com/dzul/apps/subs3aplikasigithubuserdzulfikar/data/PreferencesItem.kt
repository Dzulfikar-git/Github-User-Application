package com.dzul.apps.subs3aplikasigithubuserdzulfikar.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PreferencesItem (
    var time: String? = null,
    var isSet: Boolean = false
) : Parcelable
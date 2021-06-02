package com.bangkit.whatdish.data.source.local

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodEntity(
    var imageUri: Uri,
    var imageTitle: String,
    var nameFood: String?,
    var infoFood: String?
): Parcelable

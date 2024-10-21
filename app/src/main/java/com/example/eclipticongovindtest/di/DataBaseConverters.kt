package com.example.eclipticongovindtest.di
import android.net.Uri
import androidx.room.TypeConverter
class DataBaseConverters {
        @TypeConverter
        fun fromUri(uri: Uri?): String? {
            return uri?.toString()
        }

        @TypeConverter
        fun toUri(uriString: String?): Uri? {
            return uriString?.let { Uri.parse(it) }
        }


}
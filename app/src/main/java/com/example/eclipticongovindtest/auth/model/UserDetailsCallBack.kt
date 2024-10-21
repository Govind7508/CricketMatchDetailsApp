package com.example.eclipticongovindtest.auth.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDetails")
data class UserDetailsCallBack (var username : String , var lastname :String ,var phone :String ,var location :String,var image :Uri?){
    @PrimaryKey(autoGenerate = true)
    var id: Int =1
}
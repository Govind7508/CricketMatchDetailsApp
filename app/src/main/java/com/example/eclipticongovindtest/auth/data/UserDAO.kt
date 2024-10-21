package com.example.eclipticongovindtest.auth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.eclipticongovindtest.auth.model.UserDetailsCallBack

@Dao
interface UserDAO {
    @Query("SELECT * FROM userDetails ORDER BY id ASC")
    suspend fun getUser(): UserDetailsCallBack

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(user: UserDetailsCallBack)

    @Delete
     fun deleteUser(user : UserDetailsCallBack)

    @Update
    suspend fun updateUser(user :UserDetailsCallBack)
}
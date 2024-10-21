package com.example.eclipticongovindtest.auth.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eclipticongovindtest.auth.model.UserDetailsCallBack
import com.example.eclipticongovindtest.di.DataBaseConverters

@Database(entities = [UserDetailsCallBack::class], version = 1)
@TypeConverters(DataBaseConverters::class)
abstract class UserRoomDataBase : RoomDatabase(){
    abstract fun userDao():UserDAO
    companion object{
        @Volatile
        private var INSTANCE : UserRoomDataBase? = null
        private val DATA= Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(DATA){
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context):UserRoomDataBase{
            return Room.databaseBuilder(
                context.applicationContext,
                UserRoomDataBase::class.java,
                "NotePadApp"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

    }
}
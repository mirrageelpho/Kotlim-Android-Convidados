package com.example.convidados.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.convidados.service.model.GuestModel


@Database(entities = [GuestModel::class], version = 1)
abstract class GuestDataBase : RoomDatabase() {

    abstract fun guestDao(): GuestDao

    companion object {

        private lateinit var DBINSTANCE: GuestDataBase

        fun getDatabase(context: Context): GuestDataBase {
            if (!::DBINSTANCE.isInitialized) {
                synchronized(GuestDataBase::class){
                    DBINSTANCE = Room.databaseBuilder(context, GuestDataBase::class.java, "guestDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return DBINSTANCE
        }

    }
}
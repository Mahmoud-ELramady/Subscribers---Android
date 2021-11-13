package com.example.roomdemo.db

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class],version = 1)
abstract class SubscribeDataBase : RoomDatabase() {
    abstract val subsDao : SubscribeDao

    companion object{
        @Volatile
        private var INSTANCE:SubscribeDataBase ?= null

        fun getInstance(context: Context):SubscribeDataBase{
            synchronized(this){
                var instance:SubscribeDataBase?= INSTANCE
                if (instance==null){
                    instance= Room.databaseBuilder(context.applicationContext
                        ,SubscribeDataBase::class.java
                        ,"subs_data_base").build()
                }
                return instance
            }
        }

    }


}
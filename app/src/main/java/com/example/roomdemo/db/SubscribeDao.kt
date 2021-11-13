package com.example.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscribeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubs(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubs(subscriber: Subscriber):Int

    @Delete
    suspend fun deleteSubs(subscriber: Subscriber) :Int

    @Query("DELETE FROM data_table ")
    suspend fun deleteAll():Int

    @Query("select * from data_table")
     fun getAll(): LiveData<List<Subscriber>>





}
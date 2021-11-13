package com.example.roomdemo.db

class SubscriberRepositary(private val dao:SubscribeDao) {

    val subscribers=dao.getAll()

    suspend fun insert(subscriber: Subscriber):Long{
      return  dao.insertSubs(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int{
       return dao.updateSubs(subscriber)
    }

    suspend fun delete(subscriber: Subscriber):Int{
       return dao.deleteSubs(subscriber)
    }
    suspend fun deletaAll():Int{
        return dao.deleteAll()
    }

}
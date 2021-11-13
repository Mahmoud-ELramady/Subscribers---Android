package com.example.roomdemo

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepositary
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscribeViewModel(private val repo: SubscriberRepositary):ViewModel(),Observable {

    var subscribers=repo.subscribers

    private var isUpdatedOrDeleted=false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    val inputName=MutableLiveData<String>()

    @Bindable
    val inputEmail=MutableLiveData<String>()

    @Bindable
    val saveOrUpdataButtonText=MutableLiveData<String>()

    @Bindable
    val clearAllButtonText=MutableLiveData<String>()

    private val statusMessage=MutableLiveData<Event<String>>()
    val Message:LiveData<Event<String>>
      get() = statusMessage

    init {
        saveOrUpdataButtonText.value="Save"
        clearAllButtonText.value="Clear All"
    }

    fun saveOrUpdata(){

        if (inputName==null){
            statusMessage.value= Event("Please enter Subscriber's name")
        }else if (inputEmail==null){
            statusMessage.value= Event("Please enter Subscriber's email")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value= Event("Please enter an Correct email")
        }else {

            if (isUpdatedOrDeleted) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputEmail.value = null
                inputName.value = null
            }
        }
    }

    fun clearAllOrDelete(){
        if (isUpdatedOrDeleted){
            delete(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun insert(subscriber: Subscriber):Job = viewModelScope.launch {
          val newRowId=  repo.insert(subscriber)
        if (newRowId>-1){
            statusMessage.value= Event("Subscriber inserted Successfully $newRowId")

        }else{
            statusMessage.value= Event("Error Occurred")
        }

        }

    fun update(subscriber: Subscriber):Job=viewModelScope.launch {
     val rowNum = repo.update(subscriber)
        if (rowNum>-1){
        inputName.value=null
        inputEmail.value=null
        isUpdatedOrDeleted=false
        saveOrUpdataButtonText.value="Save"
        clearAllButtonText.value="Clear All"

            statusMessage.value=Event("Row $rowNum Updated Successfully")
        }else{
            statusMessage.value= Event("Error Occurred")

        }
    }



    fun initUpdataeAndDelete(subscriber: Subscriber){
        inputName.value=subscriber.name
        inputEmail.value=subscriber.email
        isUpdatedOrDeleted=true
        subscriberToUpdateOrDelete=subscriber
        saveOrUpdataButtonText.value="Update"
        clearAllButtonText.value="delete"
    }





    fun delete(subscriber: Subscriber):Job=viewModelScope.launch {
       val rowDelete= repo.delete(subscriber)
        if (rowDelete>0) {
            inputName.value = null
            inputEmail.value = null
            isUpdatedOrDeleted = false
            saveOrUpdataButtonText.value = "Save"
            clearAllButtonText.value = "Clear All"
            statusMessage.value = Event("Row $rowDelete deleted Successfully")
        }else{
            statusMessage.value= Event("Error Occurred")

        }
    }

    fun clearAll():Job=viewModelScope.launch {
     val rowsDeletes =  repo.deletaAll()
        if (rowsDeletes>0){
            statusMessage.value=Event("Rows $rowsDeletes Deleted Successfully")
        }else{
            statusMessage.value= Event("Error Occurred")

        }

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }




}
package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.service.model.GuestModel
import com.example.convidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application): AndroidViewModel(application) { //Passo uma parametro do tipo Application no construtor para ter um contexto
    private val mContext = application.applicationContext //Acesso o contexto através do application context
    private val mGuestRepository: GuestRepository = GuestRepository(mContext) //passo o contexto para o getInstance
    private var mSaveGuest = MutableLiveData<Boolean>()

    var SaveGuest: LiveData<Boolean> = mSaveGuest

    private var mGuest = MutableLiveData<GuestModel>()
    var guest: LiveData<GuestModel> = mGuest

    fun save(id: Int, name: String, presence: Boolean) {
        val guest = GuestModel().apply {
            this.id = id
            this.name = name
            this.presence = presence
        }

        if (id==0) {
            mSaveGuest.value = mGuestRepository.save(guest)
        } else {
            mSaveGuest.value = mGuestRepository.update(guest)
        }

    }

    fun load(id: Int){
        mGuest.value = mGuestRepository.get(id)
    }
}
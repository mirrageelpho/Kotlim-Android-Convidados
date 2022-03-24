package com.example.convidados.service.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.service.constants.DataBaseConstants
import com.example.convidados.service.model.GuestModel
import java.lang.Exception

class GuestRepository(context: Context) {

    private val mDataBase = GuestDataBase.getDatabase(context).guestDao()

    @SuppressLint("Range") //Dúvida
    fun get(id: Int):GuestModel {
        return mDataBase.get(id)
    }

    fun save(guest: GuestModel): Boolean{
        return mDataBase.save(guest) > 0
    }

    fun update(guest: GuestModel):Boolean {
        return mDataBase.update(guest) > 0
    }

    fun delete(guest: GuestModel){
        return mDataBase.delete(guest)
    }

    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {
        return mDataBase.getInvited()
    }

    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        return mDataBase.getPresent()
    }

    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        return mDataBase.getAbsent()
    }


}
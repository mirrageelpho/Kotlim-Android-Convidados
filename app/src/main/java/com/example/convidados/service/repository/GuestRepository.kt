package com.example.convidados.service.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.service.constants.DataBaseConstants
import com.example.convidados.service.model.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context) {

    //Singleton - Evita concorrencia de conexões com o banco
    /*
    * Fechado o construtor
    * criado um método estático
    * que retorna uma instancia da classe
    * Apenas ele pode retornar uma instancia da classe
    * */

    private var mGuestDatabaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    //Criando método estático

    companion object {

        private lateinit var  repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {

            if(!::repository.isInitialized){//Verifico se a variável repository foi inicializada
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    @SuppressLint("Range") //Dúvida
    fun get(id: Int):GuestModel? {

        var guest: GuestModel? = null
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val projection = arrayOf(DataBaseConstants.GUEST.COLUMNS.NAME, DataBaseConstants.GUEST.COLUMNS.PRESENCE)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if(cursor != null && cursor.count > 0) {

                cursor.moveToFirst()
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, presence)
            }

            cursor?.close()
            guest
        } catch (e: Exception) {
            guest
        }
    }


    fun save(guest: GuestModel): Boolean{
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel):Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean{
        return try {
            val db = mGuestDatabaseHelper.writableDatabase
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)


            true
        } catch (e: Exception) {
            false
        }
    }



    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        //var guest: GuestModel? = null
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"

            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if( cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }


            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
        return list
    }

    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val cursor = db.rawQuery("Select id, name, presence FROM Guest WHERE presence = 1", null)

            if( cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }


            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
        return list
    }

    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val cursor = db.rawQuery("Select id, name, presence FROM Guest WHERE presence = 0", null)

            if( cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }


            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
        return list
    }


}
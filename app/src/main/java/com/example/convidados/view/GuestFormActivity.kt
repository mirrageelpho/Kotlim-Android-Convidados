package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_guest_form)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setListeners()
        observe()
        loadData()

        binding.radioPresent.isChecked = true
    }

    private fun setListeners(){
        binding.buttonSave.setOnClickListener{handleClick()}
    }

    private fun observe() {
        mViewModel.SaveGuest.observe(this, Observer {
            if (it){
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {

            binding.editName.setText(it.name)

            if(it.presence) {
                binding.radioPresent.isChecked = true
            }
            else {
                binding.radioAbsent.isChecked = true
            }
        })
    }
    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }
    private fun handleClick(){
        val name = binding.editName.text.toString()
        val presence = binding.radioPresent.isChecked
        mViewModel.save(mGuestId, name, presence)
    }

}
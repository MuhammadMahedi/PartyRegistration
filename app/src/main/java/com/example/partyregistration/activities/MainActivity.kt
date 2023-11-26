package com.example.partyregistration.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.partyregistration.R
import com.example.partyregistration.data.Party
import com.example.partyregistration.databinding.ActivityMainBinding
import com.example.partyregistration.viewModels.PartyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : PartyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val secretary = binding.etSecretary.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()

            if(name.isEmpty()){
                Toast.makeText(this, "Please Enter Party's name", Toast.LENGTH_SHORT).show()
            }
            else if(secretary.isEmpty()){
                Toast.makeText(this, "Please Enter Secretary's name", Toast.LENGTH_SHORT).show()
            }
            else if(!(phone.startsWith("01")) || phone.length!=11 ){
                Toast.makeText(this, "Please Enter a valid BD mobile number of 11 digits", Toast.LENGTH_SHORT).show()
            }
            else if(email.isEmpty() || !(isValidEmail(email))){
                Toast.makeText(this, "Please Enter a proper email address", Toast.LENGTH_SHORT).show()
            }else{

                if(isDataExist(name, email, phone)){
                    Toast.makeText(this, "Name , Phone Number or email already exist", Toast.LENGTH_SHORT).show()
                }else{
                    val party = Party(0,name,secretary,phone,email,scanResult)
                    viewModel.addData(party)
                    Toast.makeText(this, "Party Registered successfully", Toast.LENGTH_SHORT).show()
                }

            }
        }



        binding.btnScan.setOnClickListener {
            //startQRScanner()
            //checkCameraPermission()
            startActivity(Intent(this,ScanActivity::class.java))
        }
        binding.tvScan.setOnClickListener {
            if(scanResult!=""){
                binding.tvScan.text = scanResult
            }else{
                Toast.makeText(this, "Scan a valid QR Code With proper URL", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addDoc.setOnClickListener {
            Toast.makeText(this, scanResult, Toast.LENGTH_SHORT).show()
        }



        viewModel.getAllParties()
        viewModel.allParties.observe(this){
           if(it.isNotEmpty()){
               Log.d("partyDetails",it.toString())
           }

        }

    }

    fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isDataExist(name: String ,email: String ,phone:String):Boolean{
        return (viewModel.checkDuplicate(name,email, phone) > 0)
    }




    companion object{
        var scanResult = ""
    }


}
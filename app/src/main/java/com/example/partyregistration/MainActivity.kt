package com.example.partyregistration

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
                    val party = Party(0,name,secretary,phone,email)
                    viewModel.addData(party)
                    Toast.makeText(this, "Party Registered successfully", Toast.LENGTH_SHORT).show()
                }

            }




        }

        binding.btnScan.setOnClickListener {
            //startQRScanner()
            checkCameraPermission()
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

    // Function to start QR code scanner
    private fun startQRScanner() {
        val intent = Intent("com.google.zxing.client.android.SCAN")
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
        startActivityForResult(intent, QR_CODE_REQUEST_CODE)
    }


    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            // Permission is already granted, start QR scanner
            startQRScanner()
        }
    }

    // Handle the result of the QR code scan
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QR_CODE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val contents = data?.getStringExtra("SCAN_RESULT")
            // Handle the scanned QR code contents here
            // 'contents' variable contains the scanned QR code data
        }
    }

    companion object{
        const val QR_CODE_REQUEST_CODE = 10
        const val CAMERA_PERMISSION_CODE = 100
    }


}
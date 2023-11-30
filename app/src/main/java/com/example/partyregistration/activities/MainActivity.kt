package com.example.partyregistration.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
                binding.etName.error = "Please Enter Party's name"
                Toast.makeText(this, "Please Enter Party's name", Toast.LENGTH_SHORT).show()
            }
            else if(secretary.isEmpty()){
                binding.etSecretary.error ="Please Enter Secretary's name"
                Toast.makeText(this, "Please Enter Secretary's name", Toast.LENGTH_SHORT).show()
            }
            else if(!(phone.startsWith("01")) || phone.length!=11 ){
                binding.etPhone.error = "Please Enter a valid BD mobile number of 11 digits"
                Toast.makeText(this, "Please Enter a valid BD mobile number of 11 digits", Toast.LENGTH_SHORT).show()
            }
            else if(email.isEmpty() || !(isValidEmail(email))){
                binding.etEmail.error = "Please Enter a proper email address"
                Toast.makeText(this, "Please Enter a proper email address", Toast.LENGTH_SHORT).show()
            }else{

//                if(isDataExist(name, email, phone)){
//                    Toast.makeText(this, "Name , Phone Number or email already exist", Toast.LENGTH_SHORT).show()
//                }else
                if(isNameExist(name)){
                    binding.etName.error = "Name already exist"
                }else if(isPhoneExist(phone)){
                    binding.etPhone.error = "Phone already exist"
                }else if(isEmailExist(email)){
                    binding.etEmail.error = "Email already exist"
                }
                else{
                    val party = Party(0,name,secretary,phone,email,scanResult, pdfUri.toString())
                    viewModel.addData(party)
                    clearAll()
                    Toast.makeText(this, "Party Registered successfully", Toast.LENGTH_SHORT).show()
                    var list : List<Party>? = null
                    viewModel.allParties.observe(this@MainActivity){
                      list= it
                    }
                    gotoTheLists(viewModel.allParties.value)
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

            if(pdfUri!=null){
                Toast.makeText(this, pdfUri.toString(), Toast.LENGTH_SHORT).show()
                Log.d("pdfPath",pdfUri.toString())
            }
                val intent = Intent(this, DisplayPdfActivity::class.java)
//                intent.putExtra("pdfPath", pdfPath)
                startActivity(intent)

        }

        binding.btnPartyList.setOnClickListener {
            viewModel.allParties.observe(this@MainActivity){
                //gotoTheLists(it)

            }

            val intent = Intent(this, PartyListActivity::class.java)
            startActivity(intent)
        }



        viewModel.getAllParties()
        viewModel.allParties.observe(this){
           if(it.isNotEmpty()){
               Log.d("partyDetails",it.toString())
           }

        }

    }

    private fun clearAll() {
        with(binding){
            this.etName.text?.clear()
            this.etSecretary.text?.clear()
            this.etPhone.text?.clear()
            this.etEmail.text?.clear()
            this.tvScan.text =" Show Website Url"
            this.etEmail.clearFocus()
            scanResult=""
        }
    }

    fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isDataExist(name: String ,email: String ,phone:String):Boolean{
        return (viewModel.checkDuplicate(name,email, phone) > 0)
    }
    fun isNameExist(name: String):Boolean{
        return (viewModel.checkNameExist(name) > 0)
    }
    fun isPhoneExist(phone:String):Boolean{
        return (viewModel.checkPhoneExist(phone) > 0)
    }
    fun isEmailExist(email: String):Boolean{
        return (viewModel.checkEmailExist(email) > 0)
    }

    private fun gotoTheLists(list: List<Party>?){
        val intent = Intent(this, PartyListActivity::class.java)

            //list = viewModel.allParties.value
            // Convert List<Party> to ArrayList<Party>
            val parcelablePartyList: ArrayList<Party>? = list?.let {
                ArrayList<Party>(it)
            }
            intent.putParcelableArrayListExtra("PartyList",parcelablePartyList)
            startActivity(intent)


    }


    companion object{
        var scanResult = ""
        var pdfUri : Uri? = null
    }


}
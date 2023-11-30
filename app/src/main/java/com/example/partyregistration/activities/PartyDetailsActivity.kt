package com.example.partyregistration.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.example.partyregistration.R
import com.example.partyregistration.data.Party
import com.example.partyregistration.databinding.ActivityPartyDetailsBinding
import com.example.partyregistration.viewModels.PartyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
@AndroidEntryPoint
class PartyDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPartyDetailsBinding
    private val viewModel: PartyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //This codes are for A toolbar with back button.
         setToolbar()


        val party= intent.getParcelableExtra<Party>("PartyDetails")

        with(party!!){
            binding.etNameDetails.setText(this.name)
            binding.etSecretaryDetails.setText(this.secretary)
            binding.etPhoneDetails.setText(this.phone)
            binding.etEmailDetails.setText(this.email)
            binding.etWebDetails.setText(this.website)
            //binding.tvDocumentUri.text = this.document
            //this.document?.let { Log.d("showPath", it) }
            //val uri = this.document?.toUri()
            //val uri: Uri = Uri.parse(this.document)
           // displayFromUri(uri)
        }

//        binding.showDoc.setOnClickListener {
//            startActivity(Intent(this@PartyDetailsActivity,ShowDocumentActivity::class.java))
//        }

        binding.btnUpdate.setOnClickListener { 
            if(binding.etNameDetails.text.toString() == party.name &&
                binding.etSecretaryDetails.text.toString() == party.secretary &&
                binding.etPhoneDetails.text.toString() == party.phone &&
                binding.etEmailDetails.text.toString() == party.email &&
                binding.etWebDetails.text.toString() == party.website){
                Toast.makeText(this, "Nothing Updated", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@PartyDetailsActivity, PartyListActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                checkValidity(party)
            }
        }

    }

    private fun checkValidity(party:Party) {
        val name = binding.etNameDetails.text.toString()
        val secretary = binding.etSecretaryDetails.text.toString()
        val phone = binding.etPhoneDetails.text.toString()
        val email = binding.etEmailDetails.text.toString()
        val web = binding.etWebDetails.text.toString()

        if(name.isEmpty()){
            binding.etNameDetails.error = "Please Enter Party's name"
            Toast.makeText(this, "Please Enter Party's name", Toast.LENGTH_SHORT).show()
        }
        else if(secretary.isEmpty()){
            binding.etSecretaryDetails.error ="Please Enter Secretary's name"
            Toast.makeText(this, "Please Enter Secretary's name", Toast.LENGTH_SHORT).show()
        }
        else if(!(phone.startsWith("01")) || phone.length!=11 ){
            binding.etPhoneDetails.error = "Please Enter a valid BD mobile number of 11 digits"
            Toast.makeText(this, "Please Enter a valid BD mobile number of 11 digits", Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty() || !(isValidEmail(email))){
            binding.etEmailDetails.error = "Please Enter a proper email address"
            Toast.makeText(this, "Please Enter a proper email address", Toast.LENGTH_SHORT).show()
        }else{

            if(isNameExist(name) && party.name!=name){
                binding.etNameDetails.error = "Name already exist"
            }else if(isPhoneExist(phone)  && party.phone!=phone){
                binding.etPhoneDetails.error = "Phone already exist"
            }else if(isEmailExist(email) && party.email!=email){
                binding.etEmailDetails.error = "Email already exist"
            }else{
                party.name= name
                party.secretary = secretary
                party.phone= phone
                party.email = email
                party.website= web

                viewModel.updateData(party)
                Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@PartyDetailsActivity, PartyListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun setToolbar(){
        val toolbar = binding.detailsToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Party Details" // Clear default title
            setDisplayHomeAsUpEnabled(true) // Show back button
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PartyDetailsActivity, PartyListActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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

    private fun displayFromUri(uri: Uri) {
//        val pdfFile = File(uri.path) // Convert URI to File
//        binding.docPdf.fromFile(pdfFile)
//            .defaultPage(0)
//            .load()
    }
}
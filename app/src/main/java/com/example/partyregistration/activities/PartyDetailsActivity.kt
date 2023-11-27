package com.example.partyregistration.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.core.net.toUri
import com.example.partyregistration.R
import com.example.partyregistration.data.Party
import com.example.partyregistration.databinding.ActivityPartyDetailsBinding
import java.io.File

class PartyDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPartyDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //This codes are for A toolbar with back button.
         setToolbar()


        val party= intent.getParcelableExtra<Party>("PartyDetails")

        with(party!!){
            binding.tvPartyTitle.text = this.name
            binding.tvSecretary.text = this.secretary
            binding.tvPhone.text = this.phone
            binding.tvEmail.text = this.email
            binding.tvWebsite.text = this.website
            //binding.tvDocumentUri.text = this.document
            //this.document?.let { Log.d("showPath", it) }
            //val uri = this.document?.toUri()
            //val uri: Uri = Uri.parse(this.document)
           // displayFromUri(uri)
        }

//        binding.showDoc.setOnClickListener {
//            startActivity(Intent(this@PartyDetailsActivity,ShowDocumentActivity::class.java))
//        }


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

    private fun displayFromUri(uri: Uri) {
//        val pdfFile = File(uri.path) // Convert URI to File
//        binding.docPdf.fromFile(pdfFile)
//            .defaultPage(0)
//            .load()
    }
}
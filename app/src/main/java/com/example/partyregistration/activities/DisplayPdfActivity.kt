package com.example.partyregistration.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.partyregistration.R
import com.example.partyregistration.activities.MainActivity.Companion.pdfUri
import com.example.partyregistration.databinding.ActivityDisplayPdfBinding
import com.github.barteksc.pdfviewer.PDFView

class DisplayPdfActivity : AppCompatActivity() {
    lateinit var binding:ActivityDisplayPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()


        val launchPdf = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){uri ->
            uri?.let {
                binding.pdfview.fromUri(it)
                    .spacing(12)
                    .defaultPage(0)
                    .enableAnnotationRendering(false)
                    .enableDoubletap(true)
                    .load()
                binding.pdfview.fitToWidth()
                binding.pdfview.useBestQuality(true)
                Log.d("showPath", it.toString())
            }

            pdfUri = uri


        }

        launchPdf.launch("application/pdf")

        binding.fabSave.setOnClickListener {
            if(pdfUri!=null) {
                Toast.makeText(this, "Document added successfully", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun setToolbar(){
        val toolbar = binding.documentToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Select your Documents" // Clear default title
            setDisplayHomeAsUpEnabled(true) // Show back button
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
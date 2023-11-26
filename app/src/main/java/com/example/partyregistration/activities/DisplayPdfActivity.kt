package com.example.partyregistration.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
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



        //val pdfPath = intent.getStringExtra("pdfPath")
        //val webView: WebView = findViewById(R.id.pdfWebView)
//        webView.settings.javaScriptEnabled = true
//        webView.loadUrl("file:///android_asset/pdfviewer/index.html?file=$pdfPath")

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
            }

            pdfUri = uri


        }

        launchPdf.launch("application/pdf")

        binding.fabSave.setOnClickListener {
            finish()
        }
    }
}
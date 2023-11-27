package com.example.partyregistration.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partyregistration.R
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.io.FileOutputStream

class ShowDocumentActivity : AppCompatActivity() {
    private lateinit var pdfView: PDFView
    private val pdfFileName = "temp_pdf_file.pdf" // Temporary file name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_document)

        pdfView = findViewById(R.id.docview)
        val uri: Uri = Uri.parse("content://com.android.providers.media.documents/document/document%3A1000016913")


//        uri.let {
//            pdfView.fromUri(it)
//                .spacing(12)
//                .defaultPage(0)
//                .enableAnnotationRendering(false)
//                .enableDoubletap(true)
//                .load()
//            pdfView.fitToWidth()
//            pdfView.useBestQuality(true)
//            Log.d("showPath", it.toString())
// }

        val launchPdf = registerForActivityResult(
           ActivityResultContracts.GetContent()
        ){uri ->
            uri?.let {
                pdfView.fromUri(it)
                    .spacing(12)
                    .defaultPage(0)
                    .enableAnnotationRendering(false)
                    .enableDoubletap(true)
                    .load()
                pdfView.fitToWidth()
                pdfView.useBestQuality(true)
                Log.d("showPath", it.toString())
            }

            MainActivity.pdfUri = uri


        }

        launchPdf.launch("application/pdf")

        }
    }

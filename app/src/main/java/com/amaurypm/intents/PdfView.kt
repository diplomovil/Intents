package com.amaurypm.intents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView


class PdfView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)

        var pdfView = findViewById<PDFView>(R.id.pdfView)

        when{
            intent?.action == Intent.ACTION_VIEW -> {
                if(intent.type == "application/pdf"){
                    var data = intent.data
                    pdfView.fromUri(data).load()
                }
            }
        }
    }


}
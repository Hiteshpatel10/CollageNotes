package com.geekaid.collegenotes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfActivity : AppCompatActivity() {

    private lateinit var fileName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        val pdfLayout = findViewById<PDFView>(R.id.pdfViewLayout)
        val intent = intent

        if (intent.extras != null) {
            fileName = intent.extras?.getString("fileName").toString()
        }

        try {
            pdfLayout.fromUri(File("${applicationContext.getExternalFilesDir(null)}/$fileName.pdf").toUri())
                .fitEachPage(true)
                .spacing(10)
                .load()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
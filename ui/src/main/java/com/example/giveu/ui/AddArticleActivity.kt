package com.example.giveu.ui

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.giveu.domain.model.Article
import com.example.giveu.ui.databinding.AggiungiArticoloBinding
import java.io.File
import java.io.FileOutputStream

class AddArticleActivity : AppCompatActivity() {
    private lateinit var binding: AggiungiArticoloBinding
    private val viewModel: ArticleViewModel by viewModels()
    private var imageUrl = ""
    private val gallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageUrl = it.toString(); binding.cameraIcon.setImageURI(it) }
    }
    private val camera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let { imageUrl = savePhoto(it); binding.cameraIcon.setImageBitmap(it) }
        }
    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) camera.launch(null) else Toast.makeText(
                this,
                "Per scattare una foto si deve il permesso fotocamera.",
                Toast.LENGTH_LONG
            ).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AggiungiArticoloBinding.inflate(layoutInflater); setContentView(binding.root)
        val labels = listOf(
            "Seleziona categoria...",
            "Casa",
            "Elettronica",
            "Vestiti",
            "Makeup",
            "Auto",
            "Sport",
            "Animali",
            "Libri"
        )
        binding.categorySpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, labels)
        binding.backButton.setOnClickListener { finish() }
        binding.cameraIcon.setOnClickListener { chooseImage() }
        binding.saveButton.setOnClickListener { save() }
    }

    private fun chooseImage() = AlertDialog.Builder(this).setTitle("Aggiungi una foto")
        .setItems(arrayOf("Scegli dalla galleria", "Scatta una foto")) { _, choice ->
            if (choice == 0) gallery.launch("image/*") else cameraPermission.launch(Manifest.permission.CAMERA)
        }.show()

    private fun savePhoto(bitmap: Bitmap): String = try {
        val file = File(filesDir, "giveu_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.JPEG, 85, it) }
        file.toURI().toString()
    } catch (_: Exception) {
        ""
    }

    private fun save() {
        val title = binding.titleInput.text.toString().trim();
        val description = binding.descriptionInput.text.toString().trim()
        val label = binding.categorySpinner.selectedItem.toString()
        if (title.isBlank() || description.isBlank() || label == "Seleziona categoria...") {
            Toast.makeText(this, "Compila titolo, descrizione e categoria.", Toast.LENGTH_SHORT)
                .show(); return
        }
        viewModel.save(
            Article(
                title = title,
                description = description,
                imageUrl = imageUrl,
                phoneNumber = binding.phoneInput.text.toString().trim(),
                location = binding.locationInput.text.toString().trim(),
                category = categoryFor(label)
            )
        )
        Toast.makeText(this, "Oggetto salvato sul dispositivo.", Toast.LENGTH_SHORT)
            .show(); finish()
    }

    private fun categoryFor(label: String) = mapOf(
        "Casa" to "home-decoration",
        "Elettronica" to "laptops",
        "Vestiti" to "mens-shirts",
        "Makeup" to "beauty",
        "Auto" to "automotive",
        "Sport" to "sports-accessories",
        "Animali" to "groceries",
        "Libri" to "books"
    )[label] ?: label.lowercase()
}

package com.example.hw3_rateyourfavoriteanimal

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AnimalChooserActivity : AppCompatActivity() {

    var animalImages = listOf(R.id.dogImageButton, R.id.catImageButton, R.id.bearImageButton, R.id.rabbitImageButton)
    var animalRatingTextViews = listOf(R.id.dogRatingText, R.id.catRatingText, R.id.bearRatingText, R.id.rabbitRatingText)
    var animalRatingStringIDs = listOf("dogRating", "catRating", "bearRating", "rabbitRating")
    private val FILE_NAME = "MyFile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_chooser)
        for ( (index, imageId) in animalImages.withIndex() ) {
            findViewById<ImageButton>(imageId).setOnClickListener {
                val myIntent = Intent(this, AnimalRatingActivity::class.java)
                myIntent.putExtra("indexOfSelectedAnimal", index)
                startActivity(myIntent)
            }
        }
    }

    override fun onResume(){
        super.onResume()
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        for ( (index, textViewId) in animalRatingTextViews.withIndex()){
            val rating = sharedPreferences.getFloat(animalRatingStringIDs[index], 0f)
            findViewById<TextView>(textViewId).text = "Rating: $rating"
        }
    }

}
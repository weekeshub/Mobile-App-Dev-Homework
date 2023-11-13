package com.example.hw3_rateyourfavoriteanimal

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnimalRatingActivity : AppCompatActivity() {

    private val FILE_NAME = "MyFile"
    var dogRating = 0f
    var catRating = 0f
    var bearRating = 0f
    var rabbitRating = 0f
    var rating = 0f
    var animalIndexNumber = 0
    var animals = listOf("Dog", "Cat", "Bear", "Rabbit")
    var animalRatingIDs = arrayOf(dogRating, catRating, bearRating, rabbitRating)
    var animalRatingStringIDs = listOf("dogRating", "catRating", "bearRating", "rabbitRating")
    var animalImageViews = listOf(R.drawable.dog, R.drawable.cat, R.drawable.bear, R.drawable.rabbit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_rating)
        animalIndexNumber = intent.getIntExtra("indexOfSelectedAnimal", 0)
        findViewById<ImageView>(R.id.animalImageView).setImageResource(animalImageViews[animalIndexNumber])
        findViewById<TextView>(R.id.animalRatingHeaderTV).text = animals[animalIndexNumber]
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        for ( (index, ratingID) in animalRatingStringIDs.withIndex()){
            if (animalIndexNumber == index){
                rating = sharedPreferences.getFloat(ratingID, 0f)
            }
        }
        findViewById<RatingBar>(R.id.animalRatingBar).rating = rating
    }

    fun saveRating(view: View){
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val rating2 = findViewById<RatingBar>(R.id.animalRatingBar).rating
        animalRatingIDs[animalIndexNumber] = rating2
        editor.putFloat(animalRatingStringIDs[animalIndexNumber], animalRatingIDs[animalIndexNumber])
        editor.apply()
        finish()
    }

}
package com.adamsolomon.hwk3_restaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar

class FilterRatingsActivity : AppCompatActivity() {
    private lateinit var filterRatingData: RatingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_ratings)
        filterRatingData = findViewById(R.id.filterRating)
        var filterRatingsButton = findViewById<Button>(R.id.filterButton)

        filterRatingsButton.setOnClickListener {

            val filterData = filterRatingData.rating
            val resultIntent = Intent()
            resultIntent.putExtra("myData", filterData)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
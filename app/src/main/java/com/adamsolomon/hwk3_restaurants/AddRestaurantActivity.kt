package com.adamsolomon.hwk3_restaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class AddRestaurantActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private  lateinit var restaurantNameData: EditText
    private  lateinit var restaurantLocationData: EditText
    private  lateinit var restaurantRatingData: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_restaurant)
        db = FirebaseFirestore.getInstance()
        var hasButtonBeenClicked: Boolean=false
        var addRestaurantButton=findViewById<Button>(R.id.addResturantButton)
        restaurantNameData=findViewById(R.id.resutrantNameText)
        restaurantLocationData=findViewById(R.id.resutrantLocationText)
        restaurantRatingData=findViewById(R.id.resturantRating)

        addRestaurantButton.setOnClickListener {
            var nameData = restaurantNameData.text.toString()
            var locationData = restaurantLocationData.text.toString()
            var ratingData = restaurantRatingData.rating
            // hasButtonBeenClicked=true

            if (nameData.isEmpty() || locationData.isEmpty()) {
                Toast.makeText(
                    this@AddRestaurantActivity,
                    getString(R.string.seconderrorMessage),
                    Toast.LENGTH_SHORT

                )
                    .show()
                nameData = restaurantNameData.text.toString()
                locationData = restaurantLocationData.text.toString()
                ratingData = restaurantRatingData.rating
            }

            else{
            val data = hashMapOf(
                "location" to "${locationData}",
                "name" to "${nameData}",
                "rating" to ratingData
            )
            db.collection("Hwk3Restaurants")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d("MYDEBUG", "DocumentSnapshot added with ID:${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("MYDEBUG", "Error adding document", e)
                }
            val resultIntent = Intent()
            resultIntent.putExtra("myData", hasButtonBeenClicked)
            setResult(RESULT_OK, resultIntent)
            finish()
        }


        }

    }
}
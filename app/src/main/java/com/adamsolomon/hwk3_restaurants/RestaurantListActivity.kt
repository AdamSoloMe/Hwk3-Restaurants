package com.adamsolomon.hwk3_restaurants

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class RestaurantListActivity : AppCompatActivity() {
    private lateinit var recyclerViewNames : RecyclerView
    private lateinit var data : ArrayList<Resturant>
    private lateinit var resturantprimaryAdapter: ResturantAdapter
    private lateinit var db : FirebaseFirestore
    lateinit var dataActivityLauncher: ActivityResultLauncher<Intent>
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list_activity)
        recyclerViewNames = findViewById<RecyclerView>(R.id.recyclerViewRatings)
        recyclerViewNames.layoutManager = LinearLayoutManager(this)
        db = FirebaseFirestore.getInstance()
        data = ArrayList<Resturant>()
        db.collection("Hwk3Restaurants")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var resturantName = document.getString("name")
                    var resturantLocation = document.getString("location")
                    var resturantRating = document.getDouble("rating")?.toFloat()
                    data.add(
                        Resturant(
                            resturantName,
                            resturantLocation,
                            resturantRating
                        )
                    )

                    resturantprimaryAdapter = ResturantAdapter(data)
                    recyclerViewNames.adapter = resturantprimaryAdapter
                }
            }
                //if it breaks remove this
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


        dataActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val resultIntent: Intent? = result.data
                if (resultIntent != null) {

                   val returnedfilterData = resultIntent.getFloatExtra("myData", 0.0F)


                        db.collection("Hwk3Restaurants")
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    Log.d(TAG, "${document.id} => ${document.data}")
                                    var resturantName = document.getString("name")
                                    var resturantLocation = document.getString("location")
                                    var resturantRating =
                                        document.getDouble("rating")?.toFloat()

                                        data.add(
                                            Resturant(
                                                resturantName,
                                                resturantLocation,
                                                resturantRating
                                            )
                                        )
                                    if (returnedfilterData < 0.0f) {
                                        resturantprimaryAdapter = ResturantAdapter(data)
                                        recyclerViewNames.adapter = resturantprimaryAdapter
                                    }

                                    else if (returnedfilterData > -1){
                                        var filteredList: List<Resturant> = data.filter { s -> s.rating!! >= returnedfilterData }
                                       println(filteredList.toString())
                                        resturantprimaryAdapter = ResturantAdapter(filteredList)
                                        recyclerViewNames.adapter = resturantprimaryAdapter
                                    }


                                }


                            }
                            .addOnFailureListener { exception ->
                                Log.w(TAG, "Error getting documents: ", exception)
                            }



                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.resturantappmenu, menu)





        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.add_resturant_menuitem -> {
                data.clear()
                val intent = Intent(this,AddRestaurantActivity::class.java)
                dataActivityLauncher.launch(intent)

                true
            }
            R.id.filter_by_rating_menuitem ->{
                data.clear()
                val intent = Intent(this,FilterRatingsActivity::class.java)
                dataActivityLauncher.launch(intent)
                true
        }
            R.id.clear_filter_menu_item ->{
                resturantprimaryAdapter = ResturantAdapter(data)
                recyclerViewNames.adapter = resturantprimaryAdapter
                true
            }
            R.id.App_info_menuitem ->{
                val intent = Intent(this,AppInfo::class.java)
                dataActivityLauncher.launch(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }
}




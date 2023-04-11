package com.adamsolomon.hwk3_restaurants


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth
    private lateinit var LoginData: EditText
    private lateinit var Password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var loginButton=findViewById(R.id.LoginButton) as Button

        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        LoginData=findViewById(R.id.LoginData)
        Password=findViewById(R.id.Password)

        val mCurrentUser = mAuth.currentUser
        var email = LoginData.text
        var password= Password.text

        loginButton.setOnClickListener {

            try {


                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.errorMessage),
                        Toast.LENGTH_SHORT

                    )
                        .show()
                    email = LoginData.text
                    password= Password.text

                } else {
                    mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener(
                            this
                        ) { task ->
// Was the sign in successful?
                            if (task.isSuccessful) {

                                val intent = Intent(this, RestaurantListActivity::class.java)
                                startActivity(intent)

                            } else {
                                var loginErrorMessage = getString(R.string.Loginerror)
                                Toast.makeText(this@MainActivity, loginErrorMessage, Toast.LENGTH_LONG)
                                    .show()
                                email.clear()
                                password.clear()
                            }
                            email.clear()
                            password.clear()
                        }

                }
            }
            catch (e:Exception){
                e.message

            }
        }





    }

}

package com.chandan_giri017.chaps.Activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.chandan_giri017.chaps.R


class ContactListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        supportActionBar?.hide()

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }


}
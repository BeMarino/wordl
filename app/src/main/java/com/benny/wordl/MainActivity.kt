package com.benny.wordl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.benny.wordl.R

class MainActivity : AppCompatActivity() {

    lateinit var playerName: String
    var nameInput: AppCompatEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getName(view: View) {
        nameInput = findViewById(R.id.name)
        val playerName = nameInput?.text.toString()

        if (playerName?.length > 0) {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("playerName", playerName)
            startActivity(intent)
            finish()
        } else Toast.makeText(this, "Insert name to continue", Toast.LENGTH_SHORT).show()
    }


}
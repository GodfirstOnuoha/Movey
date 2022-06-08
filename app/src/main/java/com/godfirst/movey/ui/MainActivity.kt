package com.godfirst.movey.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godfirst.movey.R
import com.godfirst.movey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Movey)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trendingCard.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("title", "Trending Movies")
            startActivity(intent)
            finish()
        }

        binding.ratedCard.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("title", "Top Rated Movies")
            startActivity(intent)
            finish()
        }
    }
}
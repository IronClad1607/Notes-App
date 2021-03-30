package com.ironclad.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ironclad.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }


    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
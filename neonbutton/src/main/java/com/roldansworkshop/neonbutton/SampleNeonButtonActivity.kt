package com.roldansworkshop.neonbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class SampleNeonButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_neon_button)
        val neonButtonA = findViewById<NeonButton>(R.id.nb_sampel_a)
        neonButtonA.setOnClickListener {
            Toast.makeText(this, R.string.button_green_text, Toast.LENGTH_LONG).show()
        }

        val neonButtonB = findViewById<NeonButton>(R.id.nb_sampel_b)
        neonButtonB.setOnClickListener {
            Toast.makeText(this, R.string.button_blue_text, Toast.LENGTH_LONG).show()
        }

    }
}
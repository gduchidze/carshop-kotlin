package com.example.carshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Page 1: Shop - displays a grid of cars for the user to select.
 * On click, the car name and price are passed to PaymentActivity via Intent extras.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Car data: name and price (Double) for each car
        val cars = listOf(
            Pair("BMW M3 (F80 generation)", 38000.0),
            Pair("Mercedes-Benz CLA-Class (Second Generation)", 46400.0),
            Pair("Porsche 911 GT3 RS (991.1 Generation)", 189000.0),
            Pair("Ferrari 488 Spider", 260000.0)
        )

        // Map each card view ID to its corresponding car data index
        val cardIds = listOf(
            R.id.card_bmw,
            R.id.card_mercedes,
            R.id.card_porsche,
            R.id.card_ferrari
        )

        // Set OnClickListener for each car card
        for (i in cardIds.indices) {
            findViewById<View>(cardIds[i]).setOnClickListener {
                // Create Intent and pass car name and price to PaymentActivity
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("car_name", cars[i].first)
                intent.putExtra("car_price", cars[i].second)
                startActivity(intent)
            }
        }
    }
}

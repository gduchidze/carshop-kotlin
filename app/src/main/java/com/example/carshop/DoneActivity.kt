package com.example.carshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Page 3: Done - displays a success message after payment.
 * The "Go To First Page" button navigates back to MainActivity.
 */
class DoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done)

        // "Go To First Page" button returns to MainActivity and clears the back stack
        findViewById<View>(R.id.btn_go_first_page).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // FLAG_ACTIVITY_CLEAR_TOP ensures we return to the existing MainActivity
            // and remove PaymentActivity and DoneActivity from the stack
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}

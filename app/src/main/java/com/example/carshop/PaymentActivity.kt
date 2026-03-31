package com.example.carshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

/**
 * Page 2: Payment - displays selected car info, applies 5% discount,
 * allows choosing shipping, and calculates the total price.
 */
class PaymentActivity : AppCompatActivity() {

    // Discount rate constant
    private val discountRate = 0.05

    // Express delivery surcharge
    private val expressCost = 1700.0

    // Track whether express shipping is selected
    private var isExpressSelected = false

    // Prices used for calculation
    private var originalPrice = 0.0
    private var discountedPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Receive car data from Intent
        val carName = intent.getStringExtra("car_name") ?: ""
        originalPrice = intent.getDoubleExtra("car_price", 0.0)

        // Apply 5% discount automatically
        discountedPrice = originalPrice * (1 - discountRate)

        // Display car information
        val carNameText = findViewById<TextView>(R.id.item_car_name)
        val carPriceText = findViewById<TextView>(R.id.item_car_price)
        val carImage = findViewById<ImageView>(R.id.item_car_image)

        carNameText.text = carName
        carPriceText.text = formatPrice(originalPrice)

        // Set car image based on name
        val imageRes = getCarImageResource(carName)
        carImage.setImageResource(imageRes)

        // Set delivery date text (estimated ~3 weeks from now)
        val deliveryDate = findViewById<TextView>(R.id.delivery_date_text)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 23)
        val dateFormat = java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
        deliveryDate.text = "Delivered on or before ${dateFormat.format(calendar.time)}"

        // Shipping option views
        val optionStandard = findViewById<LinearLayout>(R.id.option_standard)
        val optionExpress = findViewById<LinearLayout>(R.id.option_express)
        val radioStandard = findViewById<View>(R.id.radio_standard)
        val radioExpress = findViewById<View>(R.id.radio_express)
        val totalPriceText = findViewById<TextView>(R.id.total_price)

        // Default: Standard shipping is selected
        selectStandard(optionStandard, optionExpress, radioStandard, radioExpress)
        updateTotal(totalPriceText)

        // OnClickListener for Standard shipping
        optionStandard.setOnClickListener {
            isExpressSelected = false
            selectStandard(optionStandard, optionExpress, radioStandard, radioExpress)
            updateTotal(totalPriceText)
        }

        // OnClickListener for Express shipping
        optionExpress.setOnClickListener {
            isExpressSelected = true
            selectExpress(optionStandard, optionExpress, radioStandard, radioExpress)
            updateTotal(totalPriceText)
        }

        // Pay button navigates to DoneActivity
        findViewById<View>(R.id.btn_pay).setOnClickListener {
            val intent = Intent(this, DoneActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Updates the visual state when Standard shipping is selected.
     */
    private fun selectStandard(
        standard: LinearLayout, express: LinearLayout,
        radioStd: View, radioExp: View
    ) {
        standard.setBackgroundResource(R.drawable.shipping_option_selected_bg)
        express.setBackgroundResource(R.drawable.shipping_option_bg)
        radioStd.setBackgroundResource(R.drawable.radio_selected)
        radioExp.setBackgroundResource(R.drawable.radio_unselected)
    }

    /**
     * Updates the visual state when Express shipping is selected.
     */
    private fun selectExpress(
        standard: LinearLayout, express: LinearLayout,
        radioStd: View, radioExp: View
    ) {
        standard.setBackgroundResource(R.drawable.shipping_option_bg)
        express.setBackgroundResource(R.drawable.shipping_option_selected_bg)
        radioStd.setBackgroundResource(R.drawable.radio_unselected)
        radioExp.setBackgroundResource(R.drawable.radio_selected)
    }

    /**
     * Recalculates and updates the total price display.
     * Total = discountedPrice + shipping cost (0 for standard, 1700 for express).
     */
    private fun updateTotal(totalPriceText: TextView) {
        val shippingCost = if (isExpressSelected) expressCost else 0.0
        val total = discountedPrice + shippingCost
        totalPriceText.text = formatPrice(total)
    }

    /**
     * Formats a Double price value as "$XX,XXX" (no decimals for whole numbers).
     */
    private fun formatPrice(price: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        formatter.maximumFractionDigits = 0
        return formatter.format(price)
    }

    /**
     * Returns the drawable resource ID for a car based on its name.
     */
    private fun getCarImageResource(carName: String): Int {
        return when {
            carName.contains("BMW") -> R.drawable.bmw_m3
            carName.contains("Mercedes") -> R.drawable.mercedes_cla
            carName.contains("Porsche") -> R.drawable.porsche_911
            carName.contains("Ferrari") -> R.drawable.ferrari_488
            else -> R.drawable.bmw_m3
        }
    }
}

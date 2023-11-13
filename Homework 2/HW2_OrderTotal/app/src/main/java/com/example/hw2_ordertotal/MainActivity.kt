package com.example.hw2_ordertotal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var subtotalAmountText: TextView
    lateinit var totalPriceText: TextView
    lateinit var mediumText: RadioButton
    lateinit var largeText: RadioButton
    lateinit var xlargeText: RadioButton
    lateinit var tipText: TextView

    var sizePrice = 0.0
    var premiumCharge = 0.0
    var toppingsPrice = 0.0
    var subtotal = 0.0
    var deliveryPrice = 0.0
    var tip = 0.0
    var tipProgress = 0
    var tipTemp = 0.0
    var totalPrice = 0.0;
    var imageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediumText = findViewById(R.id.medButton)
        largeText = findViewById(R.id.lrgButton)
        xlargeText = findViewById(R.id.xlrgButton)
        subtotalAmountText = findViewById(R.id.subtotalAmount)
        tipText = findViewById(R.id.tipAmount)
        totalPriceText = findViewById(R.id.totalAmount)

        val pizzaList = listOf("Pepperoni", "BBQ Chicken", "Margherita", "Hawaiian")
        val pizzaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pizzaList)
        val pizzaSpinner = findViewById<Spinner>(R.id.pizzaTypeSpinner)
        pizzaSpinner.adapter = pizzaAdapter
        pizzaSpinner.onItemSelectedListener = this

        findViewById<SeekBar>(R.id.tipSeekBar).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipProgress = progress
                calculateTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calculateTotal()
            }

        })
        initialize()
    }

    fun calculateSubtotal(){
        subtotal = sizePrice + toppingsPrice + premiumCharge
        val subtotalTemp:Double = String.format("%.2f", subtotal).toDouble()
        subtotalAmountText.text = "$$subtotalTemp"
    }

    fun calculateTotal(){
        tip = tipProgress * 0.01 * subtotal
        tipTemp = String.format("%.2f", tip).toDouble()
        tipText.text = "$$tipTemp"

        totalPrice = subtotal + deliveryPrice + tip
        val totalPriceTemp:Double = String.format("%.2f", totalPrice).toDouble()
        totalPriceText.text = "$$totalPriceTemp"
    }

    fun initialize(){
        imageId = 0
        subtotal = 0.0
        totalPrice = 0.0
        subtotalAmountText.toString() == "$0.00"
        totalPriceText.toString() == "$0.00"
        mediumText.toString() == "Med ($9.99)"
        largeText.toString() == "Lrg ($13.99)"
        xlargeText.toString() == "XLrg ($15.99)"
    }

    fun onRadioButtonClicked(view: View) {
        sizePrice = when (view.id) {
            R.id.medButton -> 9.99
            R.id.lrgButton -> 13.99
            else -> 15.99
        }
        calculateSubtotal()
        calculateTotal()
    }

    override fun onNothingSelected(parent: AdapterView<*>?){
        Toast.makeText(this, "Nothing is selected", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        imageId = when (position){
            0 -> R.drawable.pepperoni
            1 -> R.drawable.bbq_chicken
            2 -> R.drawable.margherita
            else -> R.drawable.hawaiian
        }
        findViewById<ImageView>(R.id.pizzaImage).setImageResource(imageId)
        if (position == 0 || position == 1){
            premiumCharge = 0.00
        } else {
            premiumCharge = 2.00
            Toast.makeText(this, "$2.00 premium charge applied.", Toast.LENGTH_SHORT).show()
        }
        calculateSubtotal()
        calculateTotal()
    }

    fun checkBoxClick(view: View){
        if (view is CheckBox){
            if (view.isChecked){
                toppingsPrice += 1.69
            } else {
                toppingsPrice -= 1.69
            }
        }
        calculateSubtotal()
        calculateTotal()
    }

    fun switchClick(view: View){
        val delivery_switch = findViewById<Switch>(R.id.deliverySwitch)
        val switchText: String
        if (delivery_switch.isChecked){
            switchText = "Yes, $2.00"
            deliveryPrice = 2.00
        } else {
            switchText = "No, $0.00"
            deliveryPrice = 0.00
        }
        delivery_switch.text = switchText
        calculateTotal()
    }

}
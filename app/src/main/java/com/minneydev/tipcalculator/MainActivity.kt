package com.minneydev.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewParent
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.EnumSet.range

class MainActivity : AppCompatActivity(){

    lateinit var tipSpinner: Spinner
    lateinit var calcButton: Button

    private var billAmount: Double = 0.0
    private var tipPercent: Double = 0.0
    private val maxTipPercent = 30
    val tipPercentsArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tipSpinner = findViewById(R.id.tip_percent_spinner)
        spinnerSetup()
        calcButton = findViewById(R.id.calc_button)
        calcButton.setOnClickListener() {v ->
            val bounceAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            v.startAnimation(bounceAnimation)
            calculate()
        }
        tipSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, getString(R.string.tip_error_message), Toast.LENGTH_SHORT).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = tipPercentsArray[position]
                when(selectedItem) {
                    "5%"  -> {tipPercent = .05}
                    "10%" -> {tipPercent = .10}
                    "15%" -> {tipPercent = .15}
                    "20%" -> {tipPercent = .20}
                    "25%" -> {tipPercent = .25}
                    "30%" -> {tipPercent = .30}
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.tip_calc_about_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_menu) {
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun spinnerSetup() {
        for (i in 5..maxTipPercent step 5) {
            tipPercentsArray.add("${i}%")
        }
        val adapter = ArrayAdapter(this,R.layout.tip_percent_spinner_item,tipPercentsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipSpinner.adapter = adapter
    }

    private fun calculate() {
        billAmount = bill_amount_editText.text.toString().toDoubleOrNull() ?: -99.99

        if (billAmount == -99.99) {
            Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        }else {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            tip_amount_textView.text = "${format.format(billAmount*tipPercent)}"
        }
    }



}

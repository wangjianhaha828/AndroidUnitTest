package com.wangjian.androidunittest.espresso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.navigation.NavigationBarView
import com.wangjian.androidunittest.R

class EspressoSimpleActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espresso_simple)

        val changeTextBtn = findViewById<Button>(R.id.changeTextBtn)
        val changeText = findViewById<TextView>(R.id.changeText)
        changeTextBtn.setOnClickListener{
            changeText.text = "Hello Espresso!"
        }

        initSpinner()
    }

    private fun initSpinner() {
        val spinner: Spinner = findViewById(R.id.planets_spinner)
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("TAG","选择position = $position")
        val spinner: TextView = findViewById(R.id.spinner_text)
        spinner.text = "position = $position"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("TAG","onNothingSelected()")
    }
}
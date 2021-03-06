package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BMIActivity : AppCompatActivity() {

    private val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    private val US_UNIT_VIEW  = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNIT_VIEW


    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbarBmiActivity)
        val actionBar = supportActionBar

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener{
            onBackPressed()
        }
        binding.btnCalculateUnits.setOnClickListener {
            if (currentVisibleView == METRIC_UNIT_VIEW) {
                if (validateMetricUnit()) {
                    val heightValue: Float =
                        binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / heightValue.pow(2)
                    displayBMIResult(bmi)
                }else {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
            else if (currentVisibleView == US_UNIT_VIEW){
                if (validateUSUnit()){
                    val usUnitHeightFeet: String = binding.etUSUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch: String = binding.etUSUnitHeightInch.text.toString()
                    val usUnitWeightValue: Float = binding.etUsUnitWeight.text.toString().toFloat()

                    val heightValue: Float = usUnitHeightValueInch.toFloat() + usUnitHeightFeet.toFloat() * 12
                    val weightValue: Float = usUnitWeightValue

                    val bmi = 703 * (weightValue / heightValue.pow(2))
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitView()
        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }else{
                makeVisibleUSUnitView()
            }
        }

        //THIS DO NOT WORK IN RADIO GROUP
//        binding.rbMetricUnits.setOnClickListener {
//            makeVisibleMetricUnitView()
//        }
//        binding.rbUsUnits.setOnClickListener {
//            makeVisibleMetricUnitView()
//        }


    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNIT_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE

        binding.etUsUnitWeight.text!!.clear()
        binding.etUSUnitHeightFeet.text!!.clear()
        binding.etUSUnitHeightInch.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.GONE
        binding.llUSUnitsHeight.visibility = View.GONE

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitView(){
        currentVisibleView = US_UNIT_VIEW
        binding.tilMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUnitHeight.visibility = View.GONE

        binding.etMetricUnitWeight.text!!.clear()
        binding.etMetricUnitHeight.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.llUSUnitsHeight.visibility = View.VISIBLE

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class I (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class II (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class III (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llDisplayBMIResult.visibility = View.VISIBLE
//        binding.tvYourBMI.visibility = View.VISIBLE
//        binding.tvBMIValue.visibility = View.VISIBLE
//        binding.tvBMIType.visibility = View.VISIBLE
//        binding.tvBMIDescription.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue // Value is set to TextView
        binding.tvBMIType.text = bmiLabel // Label is set to TextView
        binding.tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun validateMetricUnit(): Boolean {
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty())
            isValid = false
        else if (binding.etMetricUnitHeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun validateUSUnit(): Boolean{
        var isValid = true

//        if (binding.etUsUnitWeight.text.toString().isEmpty())
//            isValid = false
//        else if (binding.etUSUnitHeightFeet.text.toString().isEmpty())
//            isValid = false
//        else if (binding.etUSUnitHeightInch.text.toString().isEmpty())
//            isValid = false

        when {
            binding.etUsUnitWeight.text.toString().isEmpty() -> isValid = false
            binding.etUSUnitHeightFeet.text.toString().isEmpty() -> isValid = false
            binding.etUSUnitHeightInch.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }
}
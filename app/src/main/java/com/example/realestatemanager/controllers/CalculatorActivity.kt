package com.example.realestatemanager.controllers

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_calculator.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.math.pow
import kotlin.math.roundToInt

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // region {Initialization}
        var loanAmount: Double
        var loanTermInYears : Double
        var interestPerYear : Double
        var monthlyPayments : Double
        var totalPrincipal : Double
        var totalInterest : Double
        var totalPayment : Double
        // endregion

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion

        Log.d("CalculatorActivity", "CalculatorActivity")

        //#region {Reset Button}
        reset_button.setOnClickListener{
            et_interest.text.clear()
            et_loan_amount.text.clear()
            et_loan_year.text.clear()

            tv_monthly_payment.text = "--.--"
            tv_total_principal_paid.text = "--.--"
            tv_total_interest_paid.text = "--.--"
            tv_total_paid.text = "--.--"
        }
        //endregion

        //#region {Calculate Button}
        calculate_button.setOnClickListener {
            if (validateForm()) {
                loanAmount = et_loan_amount.text.toString().toDouble()
                loanTermInYears = et_loan_year.text.toString().toDouble()
                interestPerYear = et_interest.text.toString().toDouble()

                val months = loanTermInYears * 12
                val rate = interestPerYear / 100 / 12

                val discount = (((1 + rate).pow(months) - 1) / (rate * (1 + rate).pow(months)))

                monthlyPayments = loanAmount / discount

                totalPrincipal = loanAmount

                totalInterest = monthlyPayments * months - loanAmount

                totalPayment = totalPrincipal + totalInterest

                tv_monthly_payment.text = monthlyPayments.roundToInt().toString()
                tv_total_principal_paid.text = totalPrincipal.roundToInt().toString()
                tv_total_interest_paid.text = totalInterest.roundToInt().toString()
                tv_total_paid.text = totalPayment.roundToInt().toString()
            }
        }
        //endregion
    }

    private fun validateForm() : Boolean {
        var valid = true

        // Test EditText
        val loanAmount = et_loan_amount.text.toString()
        if (loanAmount.isEmpty()){
            et_loan_amount.error = "Required"
            valid = false
        }

        val loanTerm = et_loan_year.text.toString()
        if (loanTerm.isEmpty()){
            et_loan_year.error = "Required"
            valid = false
        }

        val loanInterest = et_interest.text.toString()
        if (loanInterest.isEmpty()){
            et_interest.error = "Required"
            valid = false
        }

        return valid
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
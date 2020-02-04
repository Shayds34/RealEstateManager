package com.example.realestatemanager.controllers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_calculator.*
import kotlin.math.pow

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // region {Initialization}
        var loanAmount: Double
        var loanTermInYears : Double
//        var loanTermInMonths : Double
        var interestPerYear : Double
        var monthlyPayments : Double
        var totalPrincipal : Double
        var totalInterest : Double
        var totalPayment : Double
        // endregion

        Log.d("CalculatorActivity", "CalculatorActivity")

        //#region {Reset Button}
        reset_button.setOnClickListener{
            et_interest.text.clear()
            et_loan_amount.text.clear()
            et_loan_month.text.clear()
            et_loan_year.text.clear()
        }
        //endregion

        //#region {Calculate Button}
        calculate_button.setOnClickListener {

            loanAmount = et_loan_amount.text.toString().toDouble()
            loanTermInYears = et_loan_year.text.toString().toDouble()
//            loanTermInMonths = et_loan_month.text.toString().toDouble()
            interestPerYear = et_interest.text.toString().toDouble()

            val months = loanTermInYears * 12
            val rate = interestPerYear / 100 / 12

            val discount = (((1 + rate).pow(months) - 1) / (rate * (1 + rate).pow(months)))

            // TODO gérer apport

            monthlyPayments = loanAmount / discount

            totalPrincipal = loanAmount

            totalInterest = monthlyPayments * months - loanAmount

            totalPayment = totalPrincipal + totalInterest

            tv_monthly_payment.text = monthlyPayments.toString()
            tv_total_principal_paid.text = totalPrincipal.toString()
            tv_total_interest_paid.text = totalInterest.toString()
            tv_total_paid.text = totalPayment.toString()
        }
        //endregion
    }

    private fun validateForm() : Boolean {
        var valid = true

        // TODO
        // Test EditText
        val type = tv_type.text.toString()
        if (type.isEmpty()){
            tv_type.error = "Required"
            valid = false
        }

        return valid
    }

}
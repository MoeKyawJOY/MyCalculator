package com.joy.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.joy.mycalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.stringExpression.observe(this){
            // Show as x but do calculation as *
            val myExpression = it.replace("*","x")
                .replace("/", "÷")
            binding.tvExpression.text = myExpression
        }

        viewModel.currentNumber.observe(this){
            // Need to observe currentNumber because of Transformations.map
        }

        viewModel.resultValue.observe(this){
            binding.tvResult.text = "= $it"
        }

        viewModel.showResult.observe(this){
            binding.tvResult.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }

        binding.btnDigit1.setOnClickListener { viewModel.addNumber("1") }
        binding.btnDigit2.setOnClickListener { viewModel.addNumber("2") }
        binding.btnDigit3.setOnClickListener { viewModel.addNumber("3") }
        binding.btnDigit4.setOnClickListener { viewModel.addNumber("4") }
        binding.btnDigit5.setOnClickListener { viewModel.addNumber("5") }
        binding.btnDigit6.setOnClickListener { viewModel.addNumber("6") }
        binding.btnDigit7.setOnClickListener { viewModel.addNumber("7") }
        binding.btnDigit8.setOnClickListener { viewModel.addNumber("8") }
        binding.btnDigit9.setOnClickListener { viewModel.addNumber("9") }
        binding.btnDigit0.setOnClickListener { viewModel.addNumber("0") }

        binding.btnPlus.setOnClickListener { viewModel.addOperator("+") }
        binding.btnMinus.setOnClickListener { viewModel.addOperator("-") }
        binding.btnMultiply.setOnClickListener { viewModel.addOperator("*") }
        binding.btnDivision.setOnClickListener { viewModel.addOperator("/") }

        binding.btnBackDelete.setOnClickListener { viewModel.backDelete() }

        binding.btnAc.setOnClickListener { viewModel.allClear() }

        binding.btnDot.setOnClickListener { viewModel.addDot() }

        binding.btnEqual.setOnClickListener {
            viewModel.calculateAnswer()
        }

    }
}
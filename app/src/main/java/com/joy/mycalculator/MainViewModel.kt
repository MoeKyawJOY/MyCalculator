package com.joy.mycalculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainViewModel: ViewModel(){

    val stringExpression:MutableLiveData<String> = MutableLiveData("")

    // By looking currentNumber value you can know
    // whether you can add any . or whether number start with zero

    val currentNumber:LiveData<String> = Transformations.map(stringExpression){
        var myExpression = it

        // Remove - from beginning if exist
        if(myExpression.isNotEmpty() && myExpression.first() == '-'){
            myExpression = myExpression.substring(1)
        }

        if(myExpression.contains(Regex("[+*/-]"))){
            val lastOperatorIndex = myExpression.lastIndexOfAny(charArrayOf('+','-','*','/'))
            return@map myExpression.substring(lastOperatorIndex+1)
        }

        myExpression
    }

    val resultValue:MutableLiveData<Double> = MutableLiveData(0.0)
    val showResult:MutableLiveData<Boolean> = MutableLiveData(false)

    fun addNumber(number: String){
        hideResult()
        // if current number start with 0 no digit can't be followed if it not contains .
        if(currentNumber.value!!.isNotEmpty() && currentNumber.value!!.first() == '0' && !currentNumber.value!!.contains(".")) return

        stringExpression.value = stringExpression.value + number
    }

    fun addOperator(operator: String) {
        hideResult()
        // +, x, ÷ sign cannot come first in expression
        if (stringExpression.value == "" && (operator == "+" || operator == "*" || operator == "/")) return
        // Operators cannot be come twice
        if (stringExpression.value != "" && stringExpression.value!!.last() in "+-*/") return

        stringExpression.value = stringExpression.value + operator

    }

    fun allClear() {
        hideResult()
        resultValue.value = 0.0
        stringExpression.value = ""
    }

    fun backDelete() {
        if(stringExpression.value!!.isNotEmpty()){
            stringExpression.value = stringExpression.value!!.substring(0,stringExpression.value!!.lastIndex)
        }
    }

    fun addDot() {
        hideResult()
        if(currentNumber.value!!.isEmpty() || currentNumber.value!!.contains(".")) return

        stringExpression.value = stringExpression.value + "."
    }

    fun calculateAnswer() {
        if(stringExpression.value == "") return
        // you must delete operator appear at last
        if(stringExpression.value!!.last() in "+-*/"){
            stringExpression.value = stringExpression.value!!.substring(0,stringExpression.value!!.lastIndex)
            if(stringExpression.value == "") return
        }

        val expression = ExpressionBuilder(stringExpression.value).build()

        try {
            val result = expression.evaluate()
            resultValue.value = result
            showResult.value = true
        }catch (e: Exception){
            Log.d("MainViewModel", "calculateAnswer: Invalid Expression")
        }

    }

    private fun hideResult(){
        if(showResult.value == true){
            showResult.value = false
        }
    }

}
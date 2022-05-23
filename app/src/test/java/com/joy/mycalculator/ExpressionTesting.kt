package com.joy.mycalculator

import net.objecthunter.exp4j.ExpressionBuilder
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun canPointComeFirst(){
        val expression = ExpressionBuilder(".100+1").build()

        val result = expression.evaluate()

        assert(result == (0.100 + 1))
    }

    @Test
    fun canPlusComeFirst(){
        val expression = ExpressionBuilder("+10+20").build()

        val result = expression.evaluate()

        assert(result == 30.0)
    }

    @Test
    fun canCrossComeFirst(){
        val expression = ExpressionBuilder("*2*1").build()

        val result = expression.evaluate()

        assert(result == 2.0)
    }


}
package org.example.trig

import org.example.MathFunction
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Система функций для x <= 0:
 * (((((sec(x) + sec(x)) / tan(x)) - csc(x)) * sin(x)) ^ 2)
 *
 * sin и cos принимаются как MathFunction, что позволяет подставлять
 * как реальные реализации, так и заглушки (стабы) при интеграционном тестировании.
 * Производные значения (sec, csc, tan) вычисляются из sinX/cosX без повторного
 * вызова ряда разложения.
 */
class TrigSystem(
    val sin: MathFunction = Sin(),
    val tan: MathFunction = Tan(),
    val sec: MathFunction = Sec(),
    val csc: MathFunction = Csc(),
    val zeroTolerance: Double = 1e-12
) : MathFunction {

    init {
        require(zeroTolerance > 0) { "zeroTolerance must be > 0" }
    }

    override fun compute(x: Double): Double {
        require(x <= 0) { "TrigSystem определена только для x <= 0" }

        val sinValue = sin.compute(x)
        requireDefinedAndFinite(sinValue, "sin", x)

        val tanValue = tan.compute(x)
        requireDefinedAndFinite(tanValue, "tan", x)
        require(abs(tanValue) >= zeroTolerance) {
            "TrigSystem: tan(x) близко к нулю в точке x=$x"
        }

        val secValue = sec.compute(x)
        requireDefinedAndFinite(secValue, "sec", x)

        val cscValue = csc.compute(x)
        requireDefinedAndFinite(cscValue, "csc", x)

        require(abs(secValue) <= Double.MAX_VALUE / 2.0) {
            "TrigSystem: переполнение при вычислении sec(x) + sec(x) в точке x=$x"
        }
        val secSum = secValue + secValue
        requireFinite(secSum, "TrigSystem: переполнение при вычислении sec(x) + sec(x) в точке x=$x")

        val ratio = secSum / tanValue
        requireFinite(ratio, "TrigSystem: переполнение при вычислении ((sec(x) + sec(x)) / tan(x)) в точке x=$x")

        val difference = ratio - cscValue
        requireFinite(difference, "TrigSystem: переполнение при вычислении (((sec(x) + sec(x)) / tan(x)) - csc(x)) в точке x=$x")

        val product = difference * sinValue
        requireFinite(product, "TrigSystem: переполнение при вычислении ((((sec(x) + sec(x)) / tan(x)) - csc(x)) * sin(x)) в точке x=$x")

        require(abs(product) <= sqrt(Double.MAX_VALUE)) {
            "TrigSystem: переполнение при возведении в квадрат в точке x=$x"
        }

        return product * product
    }

    private fun requireDefinedAndFinite(value: Double, functionName: String, x: Double) {
        require(!value.isNaN()) {
            "TrigSystem: $functionName(x) не определён в точке x=$x"
        }
        require(value.isFinite()) {
            "TrigSystem: $functionName(x) переполняется в точке x=$x"
        }
    }

    private fun requireFinite(value: Double, message: String) {
        require(value.isFinite()) { message }
    }
}

package org.example.trig

import org.example.MathFunction
import kotlin.math.abs

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
    val cos: MathFunction = Cos(Sin())
) : MathFunction {

    override fun compute(x: Double): Double {
        require(x <= 0) { "TrigSystem определена только для x <= 0" }

        val sinX = sin.compute(x)
        val cosX = cos.compute(x)

        if (abs(sinX) < 1e-14) {
            throw IllegalArgumentException("TrigSystem: sin(x) близко к нулю в точке x=$x")
        }
        if (abs(cosX) < 1e-14) {
            throw IllegalArgumentException("TrigSystem: cos(x) близко к нулю в точке x=$x")
        }

        val secX = 1.0 / cosX
        val cscX = 1.0 / sinX
        val tanX = sinX / cosX

        // (((((sec(x) + sec(x)) / tan(x)) - csc(x)) * sin(x)) ^ 2)
        val part1 = (secX + secX) / tanX
        val part2 = part1 - cscX
        val part3 = part2 * sinX
        return part3 * part3    // алгебраически = 1 в любой допустимой точке
    }
}

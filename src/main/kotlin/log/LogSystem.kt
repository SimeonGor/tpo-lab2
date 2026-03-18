package org.example.log

import org.example.MathFunction
import kotlin.math.abs

/**
 * Система функций для x > 0, x != 1:
 * (((((log_3(x) ^ 3) + log_2(x)) ^ 3) / log_5(x)) /
 *  ((log_3(x) * log_5(x)) + ((log_5(x) + log_3(x)) / (log_10(x) ^ 2))))
 *
 * Все логарифмы принимаются как MathFunction, что позволяет подставлять
 * как реальные реализации, так и заглушки при интеграционном тестировании.
 */
class LogSystem(
    val log2: MathFunction = Log2(),
    val log3: MathFunction = Log3(),
    val log5: MathFunction = Log5(),
    val log10: MathFunction = Log10(),
    val zeroTolerance: Double = 1e-12
) : MathFunction {

    init {
        require(zeroTolerance > 0) { "zeroTolerance must be > 0" }
    }

    override fun compute(x: Double): Double {
        require(x > 0) { "LogSystem определена только для x > 0" }
        if (x.isInfinite()) return Double.NaN
        require(abs(x - 1.0) > zeroTolerance) { "LogSystem не определена в точке x = 1" }

        val log2X = log2.compute(x)
        val log3X = log3.compute(x)
        val log5X = log5.compute(x)
        val log10X = log10.compute(x)
        // log5X != 0 и log10X != 0 гарантируются require(x != 1) выше,
        // так как log_a(x) = 0 тогда и только тогда, когда x = 1.

        // Числитель: (((log_3(x) ^ 3) + log_2(x)) ^ 3) / log_5(x)
        val log3Cubed = log3X * log3X * log3X
        val numeratorInner = log3Cubed + log2X
        val numeratorInnerCubed = numeratorInner * numeratorInner * numeratorInner
        val numerator = numeratorInnerCubed / log5X

        // Знаменатель: (log_3(x)*log_5(x)) + ((log_5(x)+log_3(x)) / log_10(x)^2)
        val log10Squared = log10X * log10X
        val secondPart = (log5X + log3X) / log10Squared
        val denominator = log3X * log5X + secondPart

        if (abs(denominator) < zeroTolerance) {
            throw IllegalArgumentException("LogSystem: знаменатель близко к нулю в точке x=$x")
        }

        return numerator / denominator
    }
}

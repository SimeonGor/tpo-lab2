package org.example.trig

import org.example.MathFunction
import kotlin.math.PI
import kotlin.math.abs

interface SinFunction : MathFunction

/**
 * Вычисляет sin(x) через ряд Маклорена:
 * sin(x) = Σ ((-1)^n * x^(2n+1)) / (2n+1)!
 *
 * @param eps граничная точность вычислений
 */
class Sin(val eps: Double = 1e-15) : SinFunction {

    init {
        require(eps > 0) { "eps must be > 0" }
    }

    /**
     * Вычисляет sin(x)
     *
     * @param x аргумент функции
     * @return значение sin(x)
     */
    override fun compute(x: Double): Double {

        if (x.isNaN() || x.isInfinite()) return Double.NaN
        if (x == 0.0) return x

        val reducedX = reduceAngle(x)

        var result = 0.0
        var term = reducedX
        var n = 0

        while (abs(term) > eps) {
            result += term
            n++
            term *= -reducedX * reducedX / ((2L * n) * (2L * n + 1)).toDouble()
        }

        return result
    }

    /**
     * Приводит угол к диапазону [-π, π]
     *
     * @param x аргумент для приведения
     * @return приведенное значение
     */
    private fun reduceAngle(x: Double): Double {
        val twoPi = 2 * PI
        val quotient = x / twoPi
        val k = kotlin.math.floor(quotient + 0.5).toLong()
        return x - k * twoPi
    }
}

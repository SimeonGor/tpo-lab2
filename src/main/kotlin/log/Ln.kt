package org.example.log

import org.example.MathFunction
import kotlin.math.abs

interface LnFunction : MathFunction

/**
 * Вычисляет натуральный логарифм ln(x) через ряд artanh:
 * ln(x) = 2 * Σ ((x-1)/(x+1))^(2n+1) / (2n+1)
 *
 * Для стабильной и быстрой сходимости при любых x > 0 применяется
 * редукция аргумента: x = reduced * 2^exponent,
 * где reduced ∈ (0.5, 2.0], а ln(x) = ln(reduced) + exponent * ln(2).
 * ln(2) вычисляется тем же рядом для y = 1/3 (быстрая сходимость).
 *
 * @param eps граничная точность вычислений
 */
class Ln(val eps: Double = 1e-15) : LnFunction {

    init {
        require(eps > 0) { "eps must be > 0" }
    }

    // ln(2) вычисляется один раз при инициализации через ряд с y=1/3
    private val ln2: Double by lazy { artanh(2.0) }

    override fun compute(x: Double): Double {
        require(x > 0) { "ln(x) не определен для x <= 0" }
        if (x.isNaN() || x.isInfinite()) return Double.NaN
        if (x == 1.0) return 0.0

        // Редукция аргумента: приводим x к диапазону (0.5, 2.0]
        // так что y = (reduced-1)/(reduced+1) ∈ (-1/3, 1/3] → быстрая сходимость
        var reduced = x
        var exponent = 0
        while (reduced > 2.0) {
            reduced /= 2.0
            exponent++
        }
        while (reduced <= 0.5) {
            reduced *= 2.0
            exponent--
        }

        return artanh(reduced) + exponent * ln2
    }

    /**
     * Вычисляет ln(x) через ряд 2*artanh((x-1)/(x+1)).
     * Предполагает x ∈ (0.5, 2.0] для быстрой сходимости.
     */
    private fun artanh(x: Double): Double {
        val y = (x - 1.0) / (x + 1.0)
        var result = 0.0
        var term = y  // y^1
        var n = 0
        while (abs(term) > eps * (2 * n + 1)) {
            result += term / (2 * n + 1)
            n++
            term *= y * y
        }
        return 2.0 * result
    }
}

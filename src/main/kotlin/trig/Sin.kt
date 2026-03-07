package org.example.trig

import org.example.MathFunction
import kotlin.math.PI
import kotlin.math.abs

/**
 * Вычисляет sin(x) через ряд Маклорена:
 * sin(x) = Σ ((-1)^n * x^(2n+1)) / (2n+1)!
 */
class Sin(val eps: Double = 1e-15) : MathFunction {

    override fun compute(x: Double): Double {
        // NaN и ±Infinity — возвращаем NaN: sin не определён для таких значений
        if (x.isNaN() || x.isInfinite()) return Double.NaN

        // Редукция аргумента: x = x mod 2π для стабильности
        val reducedX = reduceAngle(x)

        // Вычисляем ряд
        var result = 0.0
        var term = reducedX  // Первый член: x^1 / 1!
        var n = 0

        while (abs(term) > eps) {
            result += term
            n++
            // Следующий член: (-1)^(n+1) * x^(2n+3) / (2n+3)!
            // Используем Long для делителя, чтобы избежать переполнения Int при больших n
            term *= -reducedX * reducedX / ((2L * n) * (2L * n + 1)).toDouble()
        }

        return result
    }

    /**
     * Приводит угол к диапазону [-π, π]
     */
    private fun reduceAngle(x: Double): Double {
        val twoPi = 2 * PI
        val quotient = x / twoPi
        val k = kotlin.math.floor(quotient + 0.5).toLong()
        return x - k * twoPi
    }
}

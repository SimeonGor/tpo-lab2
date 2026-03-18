package org.example.trig

import org.example.MathFunction
import kotlin.math.PI

interface CosFunction : MathFunction

/**
 * Вычисляет cos(x) через sin: cos(x) = sin(π/2 - x)
 */
class Cos(val sin: SinFunction = Sin()) : CosFunction {

    override fun compute(x: Double): Double {
        if (x.isNaN() || x.isInfinite()) return Double.NaN
        return sin.compute(PI / 2 - x)
    }
}

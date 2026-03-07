package org.example.trig

import org.example.MathFunction
import kotlin.math.PI

/**
 * Вычисляет cos(x) через sin: cos(x) = sin(π/2 - x)
 */
class Cos(val sin: Sin = Sin()) : MathFunction {

    override fun compute(x: Double): Double {
        return sin.compute(PI / 2 - x)
    }
}

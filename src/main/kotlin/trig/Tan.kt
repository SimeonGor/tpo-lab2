package org.example.trig

import org.example.MathFunction

/**
 * Вычисляет tan(x) = sin(x)/cos(x)
 */
class Tan(val sin: Sin = Sin(), val cos: Cos = Cos()) : MathFunction {

    override fun compute(x: Double): Double {
        return sin.compute(x) / cos.compute(x)
    }
}

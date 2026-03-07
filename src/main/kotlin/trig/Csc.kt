package org.example.trig

import org.example.MathFunction

/**
 * Вычисляет csc(x) = 1/sin(x)
 */
class Csc(val sin: Sin = Sin()) : MathFunction {

    override fun compute(x: Double): Double {
        return 1.0 / sin.compute(x)
    }
}

package org.example.trig

import org.example.MathFunction

/**
 * Вычисляет sec(x) = 1/cos(x)
 */
class Sec(val cos: Cos = Cos()) : MathFunction {

    override fun compute(x: Double): Double {
        return 1.0 / cos.compute(x)
    }
}

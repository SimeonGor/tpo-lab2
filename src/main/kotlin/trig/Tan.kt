package org.example.trig

import org.example.MathFunction
import kotlin.math.abs

/**
 * Вычисляет tan(x) = sin(x)/cos(x)
 */
class Tan(val sin: Sin = Sin(), val cos: Cos = Cos(), val zeroTolerance: Double = 1e-12) : MathFunction {

    init {
        require(zeroTolerance > 0) { "zeroTolerance must be > 0" }
    }

    override fun compute(x: Double): Double {
        if (x.isNaN() || x.isInfinite()) return Double.NaN

        val sinX = sin.compute(x)
        val cosX = cos.compute(x)

        if (sinX.isNaN() || cosX.isNaN()) return Double.NaN
        if (abs(cosX) < zeroTolerance) return Double.NaN

        return sinX / cosX
    }
}

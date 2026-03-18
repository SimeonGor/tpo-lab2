package org.example.trig

import org.example.MathFunction
import kotlin.math.abs

/**
 * Вычисляет sec(x) = 1/cos(x)
 */
class Sec(val cos: Cos = Cos(), val zeroTolerance: Double = 1e-12) : MathFunction {

    init {
        require(zeroTolerance > 0) { "zeroTolerance must be > 0" }
    }

    override fun compute(x: Double): Double {
        if (x.isNaN() || x.isInfinite()) return Double.NaN

        val cosX = cos.compute(x)
        if (cosX.isNaN()) return Double.NaN
        if (abs(cosX) < zeroTolerance) return Double.NaN

        return 1.0 / cosX
    }
}

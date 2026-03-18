package org.example.trig

import org.example.MathFunction
import kotlin.math.abs

interface CscFunction : MathFunction

/**
 * Вычисляет csc(x) = 1/sin(x)
 */
class Csc(val sin: SinFunction = Sin(), val zeroTolerance: Double = 1e-12) : CscFunction {

    init {
        require(zeroTolerance > 0) { "zeroTolerance must be > 0" }
    }

    override fun compute(x: Double): Double {
        if (x.isNaN() || x.isInfinite()) return Double.NaN

        val sinX = sin.compute(x)
        if (sinX.isNaN()) return Double.NaN
        if (abs(sinX) < zeroTolerance) return Double.NaN // csc не определён

        return 1.0 / sinX
    }
}

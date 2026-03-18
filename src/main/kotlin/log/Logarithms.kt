package org.example.log

import org.example.MathFunction

interface Log2Function : MathFunction
interface Log3Function : MathFunction
interface Log5Function : MathFunction
interface Log10Function : MathFunction

/**
 * Вычисляет log_2(x) = ln(x) / ln(2)
 */
class Log2(val ln: LnFunction = Ln()) : Log2Function {
    private val ln2 = ln.compute(2.0)

    override fun compute(x: Double): Double {
        if (x.isInfinite()) return Double.NaN
        return ln.compute(x) / ln2
    }
}

/**
 * Вычисляет log_3(x) = ln(x) / ln(3)
 */
class Log3(val ln: LnFunction = Ln()) : Log3Function {
    private val ln3 = ln.compute(3.0)

    override fun compute(x: Double): Double {
        if (x.isInfinite()) return Double.NaN
        return ln.compute(x) / ln3
    }
}

/**
 * Вычисляет log_5(x) = ln(x) / ln(5)
 */
class Log5(val ln: LnFunction = Ln()) : Log5Function {
    private val ln5 = ln.compute(5.0)

    override fun compute(x: Double): Double {
        if (x.isInfinite()) return Double.NaN
        return ln.compute(x) / ln5
    }
}

/**
 * Вычисляет log_10(x) = ln(x) / ln(10)
 */
class Log10(val ln: LnFunction = Ln()) : Log10Function {
    private val ln10 = ln.compute(10.0)

    override fun compute(x: Double): Double {
        if (x.isInfinite()) return Double.NaN
        return ln.compute(x) / ln10
    }
}

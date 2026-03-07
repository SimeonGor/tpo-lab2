package org.example.stubs

import org.example.MathFunction
import kotlin.math.E

/**
 * Заглушка для Ln
 */
class LnStub : MathFunction {
    private val table = mapOf(
        1.0 to 0.0,
        E to 1.0,
        2.0 to 0.693147180559945,
        3.0 to 1.0986122886681098,
        5.0 to 1.6094379124341003,
        10.0 to 2.302585092994046,
        0.5 to -0.693147180559945,
        0.1 to -2.302585092994046
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "LnStub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("LnStub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Log2
 */
class Log2Stub : MathFunction {
    private val table = mapOf(
        1.0 to 0.0,
        2.0 to 1.0,
        4.0 to 2.0,
        8.0 to 3.0,
        16.0 to 4.0,
        0.5 to -1.0,
        0.25 to -2.0
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log2Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log2Stub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Log3
 */
class Log3Stub : MathFunction {
    private val table = mapOf(
        1.0 to 0.0,
        2.0 to 0.6309297535714573,   // ln(2)/ln(3)
        3.0 to 1.0,
        9.0 to 2.0,
        27.0 to 3.0,
        0.333333 to -1.0
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log3Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log3Stub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Log5
 */
class Log5Stub : MathFunction {
    private val table = mapOf(
        1.0 to 0.0,
        2.0 to 0.43067655807339306,  // ln(2)/ln(5)
        5.0 to 1.0,
        25.0 to 2.0,
        125.0 to 3.0,
        0.2 to -1.0
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log5Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log5Stub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Log10
 */
class Log10Stub : MathFunction {
    private val table = mapOf(
        1.0 to 0.0,
        2.0 to 0.3010299957316877,   // log10(2)
        5.0 to 0.6989700042683123,   // log10(5)
        10.0 to 1.0,
        100.0 to 2.0,
        1000.0 to 3.0,
        0.1 to -1.0,
        0.01 to -2.0
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log10Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log10Stub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для LogSystem
 */
class LogSystemStub : MathFunction {
    private val table = mapOf(
        2.0 to 0.5,
        3.0 to 1.2,
        5.0 to 2.1,
        10.0 to 3.5
    )

    override fun compute(x: Double): Double {
        require(x > 0 && x != 1.0) { "LogSystemStub определена для x > 0, x != 1" }
        return table[x] ?: throw UnsupportedOperationException("LogSystemStub не имеет значения для x=$x")
    }
}

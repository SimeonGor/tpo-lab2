package org.example.stubs

import org.example.MathFunction
import org.example.log.LnFunction
import org.example.log.Log2Function
import org.example.log.Log3Function
import org.example.log.Log5Function
import org.example.log.Log10Function
import kotlin.math.E

class LnStub : LnFunction {
    private val table = mapOf(
        1.0   to  0.0,
        E     to  1.0,
        2.0   to  0.6931471805599453,
        3.0   to  1.0986122886681098,
        5.0   to  1.6094379124341003,
        10.0  to  2.302585092994046,
        0.5   to -0.6931471805599453,
        0.1   to -2.302585092994046
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "LnStub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("LnStub не имеет значения для x=$x")
    }
}

class Log2Stub : Log2Function {
    private val table = mapOf(
        // Основные степени двойки
        0.25  to -2.0,
        0.5   to -1.0,
        1.0   to  0.0,
        2.0   to  1.0,
        4.0   to  2.0,
        8.0   to  3.0,
        16.0  to  4.0,
        // Взаимозависимые точки с другими логарифмическими стабами
        3.0   to  1.5849625007211563,   // ln(3)/ln(2)
        5.0   to  2.321928094887362,    // ln(5)/ln(2)
        10.0  to  3.321928094887362     // ln(10)/ln(2)
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log2Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log2Stub не имеет значения для x=$x")
    }
}

class Log3Stub : Log3Function {
    private val table = mapOf(
        1.0       to  0.0,
        3.0       to  1.0,
        9.0       to  2.0,
        27.0      to  3.0,
        0.333333  to -1.0,             // ≈ 1/3
        // Взаимозависимые точки с другими логарифмическими стабами
        2.0       to  0.6309297535714573,   // ln(2)/ln(3)
        5.0       to  1.4649735207179271,   // ln(5)/ln(3)
        10.0      to  2.095903274289385     // ln(10)/ln(3)
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log3Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log3Stub не имеет значения для x=$x")
    }
}

class Log5Stub : Log5Function {
    private val table = mapOf(
        1.0   to  0.0,
        5.0   to  1.0,
        25.0  to  2.0,
        125.0 to  3.0,
        0.2   to -1.0,
        // Взаимозависимые точки с другими логарифмическими стабами
        2.0   to  0.43067655807339306,   // ln(2)/ln(5)
        3.0   to  0.6826061944859854,    // ln(3)/ln(5)
        10.0  to  1.4306765580733931     // ln(10)/ln(5)
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log5Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log5Stub не имеет значения для x=$x")
    }
}

class Log10Stub : Log10Function {
    private val table = mapOf(
        0.01   to -2.0,
        0.1    to -1.0,
        1.0    to  0.0,
        10.0   to  1.0,
        100.0  to  2.0,
        1000.0 to  3.0,
        // Взаимозависимые точки с другими логарифмическими стабами
        2.0    to  0.3010299957316877,   // log10(2)
        3.0    to  0.4771212547196624,   // log10(3)
        5.0    to  0.6989700042683123    // log10(5)
    )

    override fun compute(x: Double): Double {
        require(x > 0) { "Log10Stub определена только для x > 0" }
        return table[x] ?: throw UnsupportedOperationException("Log10Stub не имеет значения для x=$x")
    }
}

class LogSystemStub : MathFunction {
    private val table = mapOf(
        2.0  to 0.5,
        3.0  to 1.2,
        5.0  to 2.1,
        10.0 to 3.5
    )

    override fun compute(x: Double): Double {
        require(x > 0 && x != 1.0) { "LogSystemStub определена для x > 0, x != 1" }
        return table[x] ?: throw UnsupportedOperationException("LogSystemStub не имеет значения для x=$x")
    }
}

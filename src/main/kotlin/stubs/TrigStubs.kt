package org.example.stubs

import org.example.MathFunction
import kotlin.math.PI

/**
 * Заглушка для Sin с табличными значениями
 */
class SinStub : MathFunction {
    // Таблица предопределённых значений
    private val table = mapOf(
        0.0 to 0.0,
        PI / 6 to 0.5,
        PI / 4 to 0.7071067811865476,
        PI / 3 to 0.8660254037844387,
        PI / 2 to 1.0,
        PI to 0.0,
        -PI / 6 to -0.5,
        -PI / 2 to -1.0,
        -PI to 0.0,
        0.5 to 0.4794255386042030,
        -0.5 to -0.4794255386042030
    )

    override fun compute(x: Double): Double {
        return table[x] ?: throw UnsupportedOperationException("SinStub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Cos
 */
class CosStub : MathFunction {
    private val table = mapOf(
        0.0 to 1.0,
        PI / 6 to 0.8660254037844387,
        PI / 4 to 0.7071067811865476,
        PI / 3 to 0.5,
        PI / 2 to 0.0,
        PI to -1.0,
        -PI / 6 to 0.8660254037844387,
        -PI / 2 to 0.0,
        -PI to -1.0,
        0.5 to 0.8775825618903728,
        -0.5 to 0.8775825618903728
    )

    override fun compute(x: Double): Double {
        return table[x] ?: throw UnsupportedOperationException("CosStub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Sec
 */
class SecStub : MathFunction {
    private val table = mapOf(
        0.0 to 1.0,
        PI / 3 to 2.0,
        0.5 to 1.13953120067
    )

    override fun compute(x: Double): Double {
        return table[x] ?: throw UnsupportedOperationException("SecStub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Csc
 */
class CscStub : MathFunction {
    private val table = mapOf(
        PI / 6 to 2.0,
        PI / 2 to 1.0,
        0.5 to 2.08582964865
    )

    override fun compute(x: Double): Double {
        return table[x] ?: throw UnsupportedOperationException("CscStub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для Tan
 */
class TanStub : MathFunction {
    private val table = mapOf(
        0.0 to 0.0,
        PI / 6 to 0.5773502691896257,
        PI / 4 to 1.0,
        PI / 3 to 1.7320508075688772,
        -PI / 6 to -0.5773502691896257,
        0.5 to 0.5463024898437905
    )

    override fun compute(x: Double): Double {
        return table[x] ?: throw UnsupportedOperationException("TanStub не имеет значения для x=$x")
    }
}

/**
 * Заглушка для TrigSystem.
 * Формула (((((sec+sec)/tan)-csc)*sin)^2) математически равна 1 везде, где определена.
 * Недопустимые точки: x=0, -π, -2π (sin=0) и x=-π/2, -3π/2 (cos=0).
 */
class TrigSystemStub : MathFunction {
    private val undefinedPoints = setOf(0.0, -PI, -2 * PI, PI / 2, -PI / 2, -3 * PI / 2)

    override fun compute(x: Double): Double {
        require(x <= 0) { "TrigSystemStub определена только для x <= 0" }
        for (p in undefinedPoints) {
            if (kotlin.math.abs(x - p) < 1e-14)
                throw IllegalArgumentException("TrigSystemStub: недопустимая точка x=$x")
        }
        return 1.0
    }
}

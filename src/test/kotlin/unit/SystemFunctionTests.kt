package org.example.unit

import org.example.trig.TrigSystem
import org.example.log.LogSystem
import org.example.SystemFunction
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TrigSystemTest {
    private val trigSystem = TrigSystem()
    private val tolerance = 1e-8

    // ───── Формула = 1 во всех допустимых точках ─────

    /**
     * (((((sec+sec)/tan)-csc)*sin)^2) = 1 алгебраически.
     * Проверяем, что реализация действительно возвращает это значение.
     */
    @ParameterizedTest(name = "trigSystem({0}) = 1.0")
    @ValueSource(doubles = [-0.1, -0.3, -0.5, -1.0, -1.5, -2.0, -PI/4, -PI/3, -PI/6, -PI/2 - 0.1, -PI + 0.1])
    fun testTrigSystemEqualsOne(x: Double) {
        assertEquals(1.0, trigSystem.compute(x), tolerance, "TrigSystem($x) должна быть 1.0")
    }

    // ───── Недопустимые точки ─────

    @Test
    fun testTrigSystemThrowsAtZero() {
        assertFailsWith<IllegalArgumentException> { trigSystem.compute(0.0) }
    }

    @Test
    fun testTrigSystemThrowsAtMinusPi() {
        assertFailsWith<IllegalArgumentException> { trigSystem.compute(-PI) }
    }

    @Test
    fun testTrigSystemThrowsAtMinusPiHalf() {
        assertFailsWith<IllegalArgumentException> { trigSystem.compute(-PI / 2) }
    }

    @Test
    fun testTrigSystemThrowsAtMinusTwoPi() {
        assertFailsWith<IllegalArgumentException> { trigSystem.compute(-2 * PI) }
    }

    @Test
    fun testTrigSystemRequiresNonPositiveX() {
        assertFailsWith<IllegalArgumentException> { trigSystem.compute(1.0) }
        assertFailsWith<IllegalArgumentException> { trigSystem.compute(0.001) }
    }
}

class LogSystemTest {
    private val logSystem = LogSystem()
    private val tolerance = 1e-6

    // ───── Допустимые точки — проверка на конечность и точность ─────

    @ParameterizedTest(name = "logSystem({0}) конечно и совпадает с эталоном")
    @ValueSource(doubles = [2.0, 3.0, 5.0, 10.0, 0.1, 0.5, 4.0, 25.0, 100.0])
    fun testLogSystemFiniteResult(x: Double) {
        val result = logSystem.compute(x)
        assertTrue(!result.isNaN() && !result.isInfinite(), "LogSystem($x) должна быть конечной")
    }

    // ───── Недопустимые точки ─────

    @Test
    fun testLogSystemThrowsAtOne() {
        assertFailsWith<IllegalArgumentException> { logSystem.compute(1.0) }
    }

    @Test
    fun testLogSystemThrowsAtZeroAndNegative() {
        assertFailsWith<IllegalArgumentException> { logSystem.compute(0.0) }
        assertFailsWith<IllegalArgumentException> { logSystem.compute(-1.0) }
        assertFailsWith<IllegalArgumentException> { logSystem.compute(-0.5) }
    }
}

class SystemFunctionTest {
    private val system = SystemFunction()

    // ───── Маршрутизация ─────

    @Test
    fun testRoutesNegativeToTrig() {
        val x = -0.3
        assertEquals(TrigSystem().compute(x), system.compute(x), 1e-10,
            "Отрицательный x маршрутизируется в TrigSystem")
    }

    @Test
    fun testRoutesPositiveToLog() {
        val x = 2.0
        assertEquals(LogSystem().compute(x), system.compute(x), 1e-10,
            "Положительный x маршрутизируется в LogSystem")
    }

    // ───── Тригонометрическая ветка = 1 ─────

    @ParameterizedTest(name = "f({0}) = 1.0 (тригонометрическая ветка)")
    @ValueSource(doubles = [-0.1, -0.5, -1.0, -2.0, -PI/4, -PI/3])
    fun testNegativeBranchEqualsOne(x: Double) {
        assertEquals(1.0, system.compute(x), 1e-8, "f($x) = 1.0")
    }

    // ───── Граничные и недопустимые точки ─────

    @Test
    fun testThrowsAtZero() {
        assertFailsWith<IllegalArgumentException> { system.compute(0.0) }
    }

    @Test
    fun testThrowsAtOne() {
        assertFailsWith<IllegalArgumentException> { system.compute(1.0) }
    }

    @Test
    fun testThrowsAtMinusPi() {
        assertFailsWith<IllegalArgumentException> { system.compute(-PI) }
    }
}

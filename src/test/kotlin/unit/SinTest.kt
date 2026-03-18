package org.example.unit

import org.example.trig.Sin
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.abs
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SinTest {

    private val sin = Sin(eps = 1e-15)
    private val tolerance = 1e-10

    // Опорные точки
    @Test fun testSinZero()           { assertEquals(0.0.toRawBits(),   sin.compute(0.0).toRawBits()) }
    @Test fun testSinNegativeZero()   { assertEquals((-0.0).toRawBits(),sin.compute(-0.0).toRawBits()) }
    @Test fun testSinPiHalf()         { assertEquals(1.0,               sin.compute(PI / 2), tolerance) }
    @Test fun testSinPi()             { assertEquals(0.0,               sin.compute(PI),     tolerance) }
    @Test fun testSinNegativePiHalf() { assertEquals(-1.0,              sin.compute(-PI / 2), tolerance) }
    @Test fun testSinNegativePi()     { assertEquals(0.0,               sin.compute(-PI),    tolerance) }
    @Test fun testSinPiSixth()        { assertEquals(0.5,               sin.compute(PI / 6), tolerance) }
    @Test fun testSinPiThird()        { assertEquals(Math.sqrt(3.0) / 2, sin.compute(PI / 3), tolerance) }
    @Test fun testSinPiQuarter()      { assertEquals(Math.sqrt(2.0) / 2, sin.compute(PI / 4), tolerance) }

    // Проблемные точки
    @ParameterizedTest
    @ValueSource(doubles = [1e-12, -1e-12, 1e-9, -1e-9])
    fun testSinNearZero(x: Double) {
        assertEquals(Math.sin(x), sin.compute(x), 1e-12)
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI / 2 - 1e-8, PI / 2 + 1e-8, -PI / 2 - 1e-8, -PI / 2 + 1e-8])
    fun testSinNearPiHalf(x: Double) {
        assertEquals(Math.sin(x), sin.compute(x), tolerance)
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI - 1e-8, PI + 1e-8, -PI - 1e-8, -PI + 1e-8])
    fun testSinNearPi(x: Double) {
        assertEquals(Math.sin(x), sin.compute(x), tolerance)
    }

    // Сравнение с эталонным Math.sin
    @ParameterizedTest(name = "sin({0}) == Math.sin({0})")
    @ValueSource(doubles = [-3.5, -2.1, -1.0, -0.7, -0.1, 0.1, 0.5, 0.7, 1.0, 2.0, 3.0, 5.0, 10.0, 100.0, 1000.0])
    fun testMatchesMathSin(x: Double) {
        assertEquals(Math.sin(x), sin.compute(x), tolerance)
    }

    // Нечётность: sin(-x) = -sin(x)
    // PI/3 ≈ 1.0471975511965976
    @ParameterizedTest(name = "sin(-{0}) == -sin({0})")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.3, 1.0471975511965976])
    fun testSinOddFunction(x: Double) {
        assertEquals(-sin.compute(x), sin.compute(-x), tolerance)
    }

    // Периодичность: sin(x + 2π) = sin(x)
    // PI/4 ≈ 0.7853981633974483, PI/3 ≈ 1.0471975511965976
    @ParameterizedTest(name = "sin({0}) == sin({0} + 2π)")
    @ValueSource(doubles = [0.0, 0.5, 1.0, 0.7853981633974483, 1.0471975511965976])
    fun testSinPeriodicity(x: Double) {
        assertEquals(sin.compute(x), sin.compute(x + 2 * PI), tolerance)
    }

    // Область значений: sin(x) ∈ [-1, 1]
    @ParameterizedTest(name = "sin({0}) ∈ [-1, 1]")
    @MethodSource("wideRangeValues")
    fun testSinRange(x: Double) {
        assertTrue(abs(sin.compute(x)) <= 1.0 + tolerance)
    }

    companion object {
        @JvmStatic
        fun wideRangeValues(): List<Double> = (-100..100 step 7).map { it * 0.3 }
    }

    // Граничные значения
    @Test
    fun testSinSmallAngleApproximation() {
        assertEquals(0.01, sin.compute(0.01), 1e-6)
    }

    @Test fun testSinNaN()              { assertTrue(sin.compute(Double.NaN).isNaN()) }
    @Test fun testSinPositiveInfinity() { assertTrue(sin.compute(Double.POSITIVE_INFINITY).isNaN()) }
    @Test fun testSinNegativeInfinity() { assertTrue(sin.compute(Double.NEGATIVE_INFINITY).isNaN()) }

    // Таблица значений
    @ParameterizedTest(name = "sin({0}) ~= {1}")
    @CsvFileSource(
        resources = ["/sin.csv"],
        numLinesToSkip = 0,
        delimiter = ';'
    )
    fun testSinFromCsv(x: Double, expected: Double) {
        val actual = sin.compute(x)
        assertEquals(expected, actual, tolerance)
    }

    // Проверка eps
    @Test
    fun testSinNegativeEps() {
        assertThrows<IllegalArgumentException> {
            Sin(eps = -1e-15)
        }
    }

    @Test
    fun testSinZeroEps() {
        assertThrows<IllegalArgumentException> {
            Sin(eps = 0.0)
        }
    }
}

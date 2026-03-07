package org.example.unit

import org.example.log.Ln
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.E
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class LnTest {

    private val ln = Ln(eps = 1e-15)
    private val tolerance = 1e-10

    // ───── Опорные точки ─────

    @Test fun testLnOne()   { assertEquals(0.0,                 ln.compute(1.0),  tolerance) }
    @Test fun testLnE()     { assertEquals(1.0,                 ln.compute(E),    tolerance) }
    @Test fun testLnTwo()   { assertEquals(0.693147180559945,   ln.compute(2.0),  tolerance) }
    @Test fun testLnThree() { assertEquals(1.0986122886681098,  ln.compute(3.0),  tolerance) }
    @Test fun testLnFive()  { assertEquals(1.6094379124341003,  ln.compute(5.0),  tolerance) }
    @Test fun testLnTen()   { assertEquals(2.302585092994046,   ln.compute(10.0), tolerance) }
    @Test fun testLnHalf()  { assertEquals(-0.693147180559945,  ln.compute(0.5),  tolerance) }

    // ───── Сравнение с эталонным Math.log ─────

    @ParameterizedTest(name = "ln({0}) == Math.log({0})")
    @ValueSource(doubles = [0.001, 0.01, 0.1, 0.5, 0.99, 1.001, 2.0, 3.0, 5.0, 10.0,
                             100.0, 1000.0, 1e6, 1e9, 1e12])
    fun testMatchesMathLog(x: Double) {
        assertEquals(Math.log(x), ln.compute(x), tolerance)
    }

    // ───── ln(a*b) = ln(a) + ln(b) ─────

    @ParameterizedTest(name = "ln({0}*{1}) == ln({0}) + ln({1})")
    @CsvSource("2.0, 3.0", "0.5, 4.0", "10.0, 10.0", "0.1, 100.0")
    fun testLnProductRule(a: Double, b: Double) {
        assertEquals(ln.compute(a) + ln.compute(b), ln.compute(a * b), tolerance)
    }

    // ───── ln(a/b) = ln(a) - ln(b) ─────

    @ParameterizedTest(name = "ln({0}/{1}) == ln({0}) - ln({1})")
    @CsvSource("10.0, 2.0", "100.0, 5.0", "1000.0, 10.0")
    fun testLnQuotientRule(a: Double, b: Double) {
        assertEquals(ln.compute(a) - ln.compute(b), ln.compute(a / b), tolerance)
    }

    // ───── Монотонность: ln(a) < ln(b) при a < b ─────

    @ParameterizedTest(name = "ln({0}) < ln({1})")
    @CsvSource("0.01, 0.1", "0.1, 0.5", "0.5, 1.0", "1.0, 2.0",
               "2.0, 5.0", "5.0, 10.0", "10.0, 100.0")
    fun testLnMonotonic(a: Double, b: Double) {
        assertTrue(ln.compute(a) < ln.compute(b))
    }

    // ───── Знак результата ─────

    @ParameterizedTest(name = "ln({0}) < 0 при x < 1")
    @ValueSource(doubles = [0.001, 0.01, 0.1, 0.5, 0.999])
    fun testLnNegativeForLessThanOne(x: Double) {
        assertTrue(ln.compute(x) < 0)
    }

    @ParameterizedTest(name = "ln({0}) > 0 при x > 1")
    @ValueSource(doubles = [1.001, 2.0, 10.0, 1000.0])
    fun testLnPositiveForGreaterThanOne(x: Double) {
        assertTrue(ln.compute(x) > 0)
    }

    // ───── Обработка ошибок ─────

    @Test
    fun testLnZeroThrows() {
        assertFailsWith<IllegalArgumentException> { ln.compute(0.0) }
    }

    @ParameterizedTest(name = "ln({0}) бросает исключение (x <= 0)")
    @ValueSource(doubles = [-0.001, -1.0, -100.0])
    fun testLnNegativeThrows(x: Double) {
        assertFailsWith<IllegalArgumentException> { ln.compute(x) }
    }
}

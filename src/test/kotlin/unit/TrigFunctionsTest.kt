package org.example.unit

import org.example.trig.Cos
import org.example.trig.Csc
import org.example.trig.Sec
import org.example.trig.Sin
import org.example.trig.Tan
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// PI/4 ≈ 0.7853981633974483
// PI/6 ≈ 0.5235987755982988
// PI/3 ≈ 1.0471975511965976
// PI/2 ≈ 1.5707963267948966

class CosTest {

    private val sin = Sin()
    private val cos = Cos(sin)

    private val tolerance = 1e-10

    @ParameterizedTest
    @CsvSource(
        "0.0, 1.0",
        "3.141592653589793, -1.0",
        "-3.141592653589793, -1.0",
        "1.5707963267948966, 0.0",
        "-1.5707963267948966, 0.0",
        "1.0471975511965976, 0.5",
        "-1.0471975511965976, 0.5",
        "0.7853981633974483, 0.7071067811865476",
        "-0.7853981633974483, 0.7071067811865476"
    )
    fun testCosReferencePoints(x: Double, expected: Double) {
        assertEquals(expected, cos.compute(x), tolerance)
    }

    @ParameterizedTest(name = "cos({0}) == Math.cos({0})")
    @ValueSource(doubles = [-3.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 3.0, 10.0, 100.0])
    fun testMatchesMathCos(x: Double) {
        assertEquals(Math.cos(x), cos.compute(x), tolerance)
    }

    @ParameterizedTest(name = "cos({0}) == cos(-{0})")
    @ValueSource(doubles = [-3.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 3.0, 10.0, 100.0])
    fun testMathCosProp(x: Double) {
        assertEquals(cos.compute(x), cos.compute(-x), tolerance)
    }

    @ParameterizedTest(name = "sin²({0}) + cos²({0}) == 1")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.0, 0.7853981633974483, 1.0471975511965976, 0.5235987755982988])
    fun testPythagorean(x: Double) {
        val s = sin.compute(x)
        val c = cos.compute(x)
        assertEquals(1.0, s * s + c * c, tolerance)
    }

    @Test fun testCosNaN() { assertTrue(cos.compute(Double.NaN).isNaN()) }
    @Test fun testCosPositiveInfinity() { assertTrue(cos.compute(Double.POSITIVE_INFINITY).isNaN()) }
    @Test fun testCosNegativeInfinity() { assertTrue(cos.compute(Double.NEGATIVE_INFINITY).isNaN()) }
}

class SecTest {
    private val sec = Sec()
    private val tolerance = 1e-10

    @Test fun testSecZero()    { assertEquals(1.0, sec.compute(0.0),    tolerance) }
    @Test fun testSecPiThird() { assertEquals(2.0, sec.compute(PI / 3), tolerance) }

    @ParameterizedTest
    @CsvSource(
        "0.7853981633974483, 1.4142135623730951",
        "-0.7853981633974483, 1.4142135623730951",
        "0.5235987755982988, 1.1547005383792515",
        "-0.5235987755982988, 1.1547005383792515",
        "1.0471975511965976, 2.0",
        "-1.0471975511965976, 2.0"
    )
    fun testSecReferencePoints(x: Double, expected: Double) {
        assertEquals(expected, sec.compute(x), tolerance)
    }

    // sec чётная функция: sec(-x) = sec(x)
    @ParameterizedTest(name = "sec({0}) == sec(-{0})")
    @ValueSource(doubles = [0.3, 0.7, 1.0, 0.7853981633974483])
    fun testSecEvenFunction(x: Double) {
        assertEquals(sec.compute(x), sec.compute(-x), tolerance)
    }

    @ParameterizedTest(name = "sec({0}) == 1/Math.cos({0})")
    @ValueSource(doubles = [-1.0, -0.5, 0.1, 0.5, 1.0, 2.0])
    fun testMatchesReciprocal(x: Double) {
        assertEquals(1.0 / Math.cos(x), sec.compute(x), tolerance)
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI / 2, -PI / 2, 3 * PI / 2, -3 * PI / 2])
    fun testSecUndefinedPoints(x: Double) {
        assertTrue(sec.compute(x).isNaN())
    }

    @Test
    fun testSecZeroToleranceMustBePositive() {
        assertThrows<IllegalArgumentException> {
            Sec(zeroTolerance = 0.0)
        }
        assertThrows<IllegalArgumentException> {
            Sec(zeroTolerance = -1e-12)
        }
    }

    @Test fun testSecNaN() { assertTrue(sec.compute(Double.NaN).isNaN()) }
    @Test fun testSecPositiveInfinity() { assertTrue(sec.compute(Double.POSITIVE_INFINITY).isNaN()) }
    @Test fun testSecNegativeInfinity() { assertTrue(sec.compute(Double.NEGATIVE_INFINITY).isNaN()) }
}

class CscTest {
    private val csc = Csc()
    private val tolerance = 1e-10

    @Test fun testCscPiHalf()  { assertEquals(1.0, csc.compute(PI / 2), tolerance) }
    @Test fun testCscPiSixth() { assertEquals(2.0, csc.compute(PI / 6), tolerance) }

    @ParameterizedTest
    @CsvSource(
        "1.5707963267948966, 1.0",
        "-1.5707963267948966, -1.0",
        "0.5235987755982988, 2.0",
        "-0.5235987755982988, -2.0",
        "0.7853981633974483, 1.4142135623730951",
        "-0.7853981633974483, -1.4142135623730951"
    )
    fun testCscReferencePoints(x: Double, expected: Double) {
        assertEquals(expected, csc.compute(x), tolerance)
    }

    // csc нечётная функция: csc(-x) = -csc(x)
    @ParameterizedTest(name = "csc(-{0}) == -csc({0})")
    @ValueSource(doubles = [0.3, 0.7, 0.5235987755982988, 0.7853981633974483])
    fun testCscOddFunction(x: Double) {
        assertEquals(-csc.compute(x), csc.compute(-x), tolerance)
    }

    @ParameterizedTest(name = "csc({0}) == 1/Math.sin({0})")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.0, -0.5, -1.0])
    fun testMatchesReciprocal(x: Double) {
        assertEquals(1.0 / Math.sin(x), csc.compute(x), tolerance)
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI, -PI, 2 * PI, -2 * PI])
    fun testCscUndefinedPoints(x: Double) {
        assertTrue(csc.compute(x).isNaN())
    }

    @Test
    fun testCscZeroToleranceMustBePositive() {
        assertThrows<IllegalArgumentException> {
            Csc(zeroTolerance = 0.0)
        }
        assertThrows<IllegalArgumentException> {
            Csc(zeroTolerance = -1e-12)
        }
    }

    @Test fun testCscNaN() { assertTrue(csc.compute(Double.NaN).isNaN()) }
    @Test fun testCscPositiveInfinity() { assertTrue(csc.compute(Double.POSITIVE_INFINITY).isNaN()) }
    @Test fun testCscNegativeInfinity() { assertTrue(csc.compute(Double.NEGATIVE_INFINITY).isNaN()) }
}

class TanTest {
    private val sin = Sin()
    private val cos = Cos(sin)
    private val tan = Tan(sin, cos)
    private val tolerance = 1e-10

    @Test fun testTanZero()      { assertEquals(0.0, tan.compute(0.0),    tolerance) }
    @Test fun testTanPiSixth()   { assertEquals(1.0 / Math.sqrt(3.0), tan.compute(PI / 6), tolerance) }
    @Test fun testTanPiQuarter() { assertEquals(1.0, tan.compute(PI / 4), tolerance) }
    @Test fun testTanNegativePiQuarter() { assertEquals(-1.0, tan.compute(-PI / 4), tolerance) }

    // tan(x) = sin(x) / cos(x)
    @ParameterizedTest(name = "tan({0}) == sin({0})/cos({0})")
    @ValueSource(doubles = [0.1, 0.3, 0.5, 0.5235987755982988, 0.7853981633974483])
    fun testTanDefinition(x: Double) {
        assertEquals(sin.compute(x) / cos.compute(x), tan.compute(x), tolerance)
    }

    @ParameterizedTest
    @CsvSource(
        "-1.0, -1.5574077246549023",
        "-0.5, -0.5463024898437905",
        "0.5, 0.5463024898437905",
        "1.0, 1.5574077246549023"
    )
    fun testTanReferencePoints(x: Double, expected: Double) {
        assertEquals(expected, tan.compute(x), 1e-9)
    }

    @ParameterizedTest(name = "tan(-{0}) == -tan({0})")
    @ValueSource(doubles = [0.1, 0.3, 0.5, 0.7853981633974483, 1.0])
    fun testTanOddFunction(x: Double) {
        assertEquals(-tan.compute(x), tan.compute(-x), 1e-9)
    }

    @ParameterizedTest(name = "tan({0}) == tan({0} + π)")
    @ValueSource(doubles = [-1.0, -0.5, 0.1, 0.5, 1.0])
    fun testTanPeriodicity(x: Double) {
        assertEquals(tan.compute(x), tan.compute(x + PI), 1e-9)
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI / 2, -PI / 2, 3 * PI / 2, -3 * PI / 2])
    fun testTanUndefinedAtPiHalfFamily(x: Double) {
        assertTrue(tan.compute(x).isNaN())
    }

    @ParameterizedTest
    @CsvSource(
        "1.5707963257948965",
        "1.5707963277948966",
        "-1.5707963257948965",
        "-1.5707963277948966"
    )
    fun testTanNearPiHalf(x: Double) {
        val result = tan.compute(x)
        assertTrue(result.isNaN() || kotlin.math.abs(result) > 1e6)
    }

    @Test
    fun testTanZeroToleranceMustBePositive() {
        assertThrows<IllegalArgumentException> {
            Tan(sin, cos, 0.0)
        }
        assertThrows<IllegalArgumentException> {
            Tan(sin, cos, -1e-12)
        }
    }

    @Test fun testTanNaN() { assertTrue(tan.compute(Double.NaN).isNaN()) }
    @Test fun testTanPositiveInfinity() { assertTrue(tan.compute(Double.POSITIVE_INFINITY).isNaN()) }
    @Test fun testTanNegativeInfinity() { assertTrue(tan.compute(Double.NEGATIVE_INFINITY).isNaN()) }
}

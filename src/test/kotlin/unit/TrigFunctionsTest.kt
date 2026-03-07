package org.example.unit

import org.example.trig.Cos
import org.example.trig.Csc
import org.example.trig.Sec
import org.example.trig.Sin
import org.example.trig.Tan
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// PI/4 ≈ 0.7853981633974483
// PI/6 ≈ 0.5235987755982988
// PI/3 ≈ 1.0471975511965976

class CosTest {
    private val sin = Sin()
    private val cos = Cos(sin)
    private val tolerance = 1e-10

    @Test fun testCosZero()    { assertEquals(1.0,  cos.compute(0.0),    tolerance) }
    @Test fun testCosPiHalf()  { assertEquals(0.0,  cos.compute(PI / 2), tolerance) }
    @Test fun testCosPi()      { assertEquals(-1.0, cos.compute(PI),     tolerance) }
    @Test fun testCosPiThird() { assertEquals(0.5,  cos.compute(PI / 3), tolerance) }

    @ParameterizedTest(name = "cos({0}) == Math.cos({0})")
    @ValueSource(doubles = [-3.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 3.0, 10.0, 100.0])
    fun testMatchesMathCos(x: Double) {
        assertEquals(Math.cos(x), cos.compute(x), tolerance)
    }

    @ParameterizedTest(name = "sin²({0}) + cos²({0}) == 1")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.0, 0.7853981633974483, 1.0471975511965976, 0.5235987755982988])
    fun testPythagorean(x: Double) {
        val s = sin.compute(x)
        val c = cos.compute(x)
        assertEquals(1.0, s * s + c * c, tolerance)
    }
}

class SecTest {
    private val sec = Sec()
    private val tolerance = 1e-10

    @Test fun testSecZero()    { assertEquals(1.0, sec.compute(0.0),    tolerance) }
    @Test fun testSecPiThird() { assertEquals(2.0, sec.compute(PI / 3), tolerance) }

    // sec чётная функция: sec(-x) = sec(x)
    // PI/4 ≈ 0.7853981633974483
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
}

class CscTest {
    private val csc = Csc()
    private val tolerance = 1e-10

    @Test fun testCscPiHalf()  { assertEquals(1.0, csc.compute(PI / 2), tolerance) }
    @Test fun testCscPiSixth() { assertEquals(2.0, csc.compute(PI / 6), tolerance) }

    // csc нечётная функция: csc(-x) = -csc(x)
    // PI/6 ≈ 0.5235987755982988, PI/4 ≈ 0.7853981633974483
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
}

class TanTest {
    private val sin = Sin()
    private val cos = Cos(sin)
    private val tan = Tan(sin, cos)
    private val tolerance = 1e-10

    @Test fun testTanZero()      { assertEquals(0.0, tan.compute(0.0),    tolerance) }
    @Test fun testTanPiSixth()   { assertEquals(1.0 / Math.sqrt(3.0), tan.compute(PI / 6), tolerance) }
    @Test fun testTanPiQuarter() { assertEquals(1.0, tan.compute(PI / 4), tolerance) }

    // tan(x) = sin(x) / cos(x)
    // PI/6 ≈ 0.5235987755982988, PI/4 ≈ 0.7853981633974483
    @ParameterizedTest(name = "tan({0}) == sin({0})/cos({0})")
    @ValueSource(doubles = [0.1, 0.3, 0.5, 0.5235987755982988, 0.7853981633974483])
    fun testTanDefinition(x: Double) {
        assertEquals(sin.compute(x) / cos.compute(x), tan.compute(x), tolerance)
    }

    @ParameterizedTest(name = "tan({0}) == Math.tan({0})")
    @ValueSource(doubles = [-1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5])
    fun testMatchesMathTan(x: Double) {
        assertEquals(Math.tan(x), tan.compute(x), tolerance)
    }
}

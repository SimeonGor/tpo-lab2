package org.example.unit

import org.example.log.Log2
import org.example.log.Log3
import org.example.log.Log5
import org.example.log.Log10
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Log2Test {
    private val log2 = Log2()
    private val tolerance = 1e-10

    @Test fun testLog2One()   { assertEquals(0.0,  log2.compute(1.0), tolerance) }
    @Test fun testLog2Two()   { assertEquals(1.0,  log2.compute(2.0), tolerance) }
    @Test fun testLog2Four()  { assertEquals(2.0,  log2.compute(4.0), tolerance) }
    @Test fun testLog2Eight() { assertEquals(3.0,  log2.compute(8.0), tolerance) }
    @Test fun testLog2Half()  { assertEquals(-1.0, log2.compute(0.5), tolerance) }

    @ParameterizedTest(name = "log2({0}) сравнивается с Math.log/ln(2)")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.0, 5.0, 10.0, 100.0, 1000.0])
    fun testMatchesMathLog2(x: Double) {
        val expected = Math.log(x) / Math.log(2.0)
        assertEquals(expected, log2.compute(x), tolerance, "log2($x)")
    }
}

class Log3Test {
    private val log3 = Log3()
    private val tolerance = 1e-10

    @Test fun testLog3One()         { assertEquals(0.0, log3.compute(1.0),  tolerance) }
    @Test fun testLog3Three()       { assertEquals(1.0, log3.compute(3.0),  tolerance) }
    @Test fun testLog3Nine()        { assertEquals(2.0, log3.compute(9.0),  tolerance) }
    @Test fun testLog3TwentySeven() { assertEquals(3.0, log3.compute(27.0), tolerance) }

    @ParameterizedTest(name = "log3({0}) сравнивается с Math.log/ln(3)")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.0, 5.0, 10.0, 100.0])
    fun testMatchesMathLog3(x: Double) {
        val expected = Math.log(x) / Math.log(3.0)
        assertEquals(expected, log3.compute(x), tolerance, "log3($x)")
    }
}

class Log5Test {
    private val log5 = Log5()
    private val tolerance = 1e-10

    @Test fun testLog5One()        { assertEquals(0.0, log5.compute(1.0),  tolerance) }
    @Test fun testLog5Five()       { assertEquals(1.0, log5.compute(5.0),  tolerance) }
    @Test fun testLog5TwentyFive() { assertEquals(2.0, log5.compute(25.0), tolerance) }
    @Test fun testLog5Negative() { assertTrue(log5.compute(0.25) < 0) }

    @ParameterizedTest(name = "log5({0}) сравнивается с Math.log/ln(5)")
    @ValueSource(doubles = [0.1, 0.5, 1.0, 2.0, 5.0, 10.0, 100.0])
    fun testMatchesMathLog5(x: Double) {
        val expected = Math.log(x) / Math.log(5.0)
        assertEquals(expected, log5.compute(x), tolerance, "log5($x)")
    }
}

class Log10Test {
    private val log10 = Log10()
    private val tolerance = 1e-10

    @Test fun testLog10One()        { assertEquals(0.0,  log10.compute(1.0),    tolerance) }
    @Test fun testLog10Ten()        { assertEquals(1.0,  log10.compute(10.0),   tolerance) }
    @Test fun testLog10Hundred()    { assertEquals(2.0,  log10.compute(100.0),  tolerance) }
    @Test fun testLog10Thousand()   { assertEquals(3.0,  log10.compute(1000.0), tolerance) }
    @Test fun testLog10OneHundredth(){ assertEquals(-2.0, log10.compute(0.01),   tolerance) }

    @ParameterizedTest(name = "log10({0}) сравнивается с Math.log10")
    @ValueSource(doubles = [0.001, 0.01, 0.1, 0.5, 1.0, 2.0, 5.0, 10.0, 100.0, 10000.0])
    fun testMatchesMathLog10(x: Double) {
        assertEquals(Math.log10(x), log10.compute(x), tolerance, "log10($x)")
    }
}

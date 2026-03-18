package org.example.integration

import org.example.CsvWriter
import org.example.MathFunction
import org.example.SystemFunction
import org.example.log.Ln
import org.example.log.Log2
import org.example.log.Log3
import org.example.log.Log5
import org.example.log.Log10
import org.example.log.LogSystem
import org.example.stubs.LnStub
import org.example.stubs.Log2Stub
import org.example.stubs.Log3Stub
import org.example.stubs.Log5Stub
import org.example.stubs.Log10Stub
import org.example.stubs.SecStub
import org.example.stubs.CscStub
import org.example.stubs.SinStub
import org.example.stubs.TanStub
import org.example.trig.Cos
import org.example.trig.Csc
import org.example.trig.Sec
import org.example.trig.Sin
import org.example.trig.Tan
import org.example.trig.TrigSystem
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Stage 0: все зависимости TrigSystem и LogSystem заменены заглушками.
 */
class Stage0AllStubsTest {
    private val trigSystem = TrigSystem(sin = SinStub(), tan = TanStub(), sec = SecStub(), csc = CscStub())
    private val logSystem = LogSystem(log2 = Log2Stub(), log3 = Log3Stub(), log5 = Log5Stub(), log10 = Log10Stub())

    @Test
    fun testTrigSystemWithAllStubs() {
        val result = trigSystem.compute(-0.5)
        assertEquals(1.0, result, 1e-6,
            "TrigSystem со всеми заглушками при x=-0.5 должна давать 1.0")
    }

    @Test
    fun testLogSystemWithAllStubs() {
        val result = logSystem.compute(2.0)
        assertTrue(!result.isNaN() && !result.isInfinite(),
            "LogSystem со всеми заглушками при x=2.0 должна возвращать конечное значение")
    }
}

/**
 * Stage 1: Sin заменён реальной реализацией, остальные тригонометрические зависимости - заглушки.
 */
class Stage1RealSinStubDerivativesTest {
    private val realSin = Sin()
    private val trigSystem = TrigSystem(sin = realSin, tan = TanStub(), sec = SecStub(), csc = CscStub())

    @ParameterizedTest(name = "Stage1 TrigSystem({0}) близко к 1.0")
    @ValueSource(doubles = [-0.1, -0.3, -0.5, -1.0, -PI / 4, -PI / 3])
    fun testTrigSystemEqualsOne(x: Double) {
        assertEquals(1.0, trigSystem.compute(x), 1e-6,
            "Stage1: TrigSystem($x) с реальным Sin и заглушками должна давать результат близкий к 1.0")
    }
}

/**
 * Stage 2: все зависимости TrigSystem заменены реальными реализациями.
 */
class Stage2RealAllTrigTest {
    private val realSin = Sin()
    private val trigSystem = TrigSystem(
        sin = realSin,
        tan = Tan(realSin, Cos(realSin)),
        sec = Sec(Cos(realSin)),
        csc = Csc(realSin)
    )

    @ParameterizedTest(name = "Stage2 TrigSystem({0}) = 1.0")
    @ValueSource(doubles = [-0.1, -0.3, -0.5, -1.0, -PI / 4, -PI / 3])
    fun testTrigSystemEqualsOne(x: Double) {
        assertEquals(1.0, trigSystem.compute(x), 1e-6,
            "Stage2: TrigSystem($x) с реальными зависимостями должна давать 1.0")
    }
}

/**
 * Stage 3: реальный Ln, производные логарифмы - заглушки.
 */
class Stage3RealLnStubDerivativesTest {
    private val realLn = Ln()

    @ParameterizedTest(name = "Реальный ln({0}) совпадает со значением stub")
    @ValueSource(doubles = [1.0, 2.0, 3.0, 5.0, 10.0, 0.5])
    fun testRealLnMatchesStubAtTablePoints(x: Double) {
        val stubLn = LnStub()
        assertEquals(stubLn.compute(x), realLn.compute(x), 1e-9,
            "Реальный ln($x) совпадает со значением stub")
    }

    @Test
    fun testLogSystemWithRealLnDerivedLogs() {
        val logSystem = LogSystem(
            log2 = Log2(realLn),
            log3 = Log3(realLn),
            log5 = Log5Stub(),
            log10 = Log10Stub()
        )
        val result = logSystem.compute(5.0)
        assertTrue(!result.isNaN() && !result.isInfinite(),
            "Stage3: LogSystem(RealLog2, RealLog3, StubLog5, StubLog10)(5.0) конечна")
    }
}

/**
 * Stage 4: все логарифмические зависимости заменены реальными реализациями.
 */
class Stage4RealAllLogsTest {
    private val realLn = Ln()
    private val logSystem = LogSystem(
        log2  = Log2(realLn),
        log3  = Log3(realLn),
        log5  = Log5(realLn),
        log10 = Log10(realLn)
    )

    @ParameterizedTest(name = "Stage4 LogSystem({0}) конечна")
    @ValueSource(doubles = [0.1, 0.5, 2.0, 3.0, 5.0, 10.0, 100.0])
    fun testLogSystemFiniteWithRealDeps(x: Double) {
        val result = logSystem.compute(x)
        assertTrue(!result.isNaN() && !result.isInfinite(),
            "Stage4: LogSystem($x) конечна")
    }

    @ParameterizedTest(name = "Stage4 и дефолтная LogSystem совпадают при x={0}")
    @ValueSource(doubles = [2.0, 5.0, 10.0])
    fun testLogSystemResultMatchesDefault(x: Double) {
        val defaultLogSystem = LogSystem()
        assertEquals(defaultLogSystem.compute(x), logSystem.compute(x), 1e-9,
            "Stage4 и дефолтная LogSystem совпадают при x=$x")
    }
}

/**
 * Stage 5: полная система без заглушек.
 */
class Stage5FullSystemTest {
    private val system = SystemFunction()

    @ParameterizedTest(name = "f({0}) = 1.0 (тригонометрическая ветка)")
    @ValueSource(doubles = [-0.1, -0.5, -1.0, -2.0, -PI / 4, -PI / 3])
    fun testNegativeBranchEqualsOne(x: Double) {
        assertEquals(1.0, system.compute(x), 1e-8, "f($x) = 1.0")
    }

    @ParameterizedTest(name = "f({0}) конечно (логарифмическая ветка)")
    @ValueSource(doubles = [2.0, 3.0, 5.0, 10.0, 0.1, 0.5, 4.0, 25.0, 100.0])
    fun testPositiveBranchIsFinite(x: Double) {
        val result = system.compute(x)
        assertTrue(!result.isNaN() && !result.isInfinite(), "f($x) должна быть конечной")
    }

    @ParameterizedTest(name = "SystemFunction({0}) совпадает с LogSystem({0})")
    @ValueSource(doubles = [2.0, 3.0, 5.0, 10.0])
    fun testMatchesDirectLogSystem(x: Double) {
        val logSystem = LogSystem()
        assertEquals(logSystem.compute(x), system.compute(x), 1e-10,
            "SystemFunction($x) совпадает с LogSystem($x)")
    }
}

class CsvIntegrationTest {

    private fun tmpFile(name: String): File =
        File(System.getProperty("java.io.tmpdir"), name).also { it.deleteOnExit() }

    @Test
    fun testCsvSingleFunction() {
        val sin = Sin()
        val file = tmpFile("test_sin_${System.nanoTime()}.csv")
        CsvWriter.writeToCsv(sin, 0.0, PI, 0.5, file.absolutePath)

        assertTrue(file.exists(), "CSV файл должен быть создан")
        assertTrue(file.length() > 0, "CSV файл не должен быть пустым")

        val lines = file.readLines()
        assertTrue(lines.size >= 2, "Должен быть заголовок и строки данных")
        assertTrue(lines[0].startsWith("x"), "Первая строка - заголовок")

        val firstDataRow = lines[1].split(",")
        assertEquals(2, firstDataRow.size, "Строка данных: 2 колонки")
    }

    @Test
    fun testCsvMultipleFunctions() {
        val sin: MathFunction = Sin()
        val cos: MathFunction = Cos(Sin())
        val file = tmpFile("test_multi_${System.nanoTime()}.csv")

        CsvWriter.writeToCsv(mapOf("sin" to sin, "cos" to cos), 0.0, PI / 2, 0.2, file.absolutePath)

        assertTrue(file.exists())
        val header = file.readLines().first()
        assertTrue(header.contains("sin"))
        assertTrue(header.contains("cos"))
    }

    @Test
    fun testCsvCustomDelimiter() {
        val sin: MathFunction = Sin()
        val file = tmpFile("test_semi_${System.nanoTime()}.csv")
        CsvWriter.writeToCsv(sin, 0.0, 1.0, 0.5, file.absolutePath, delimiter = ";")

        val lines = file.readLines()
        assertTrue(lines[0].contains(";"), "Заголовок должен содержать ';'")
    }

    @Test
    fun testCsvCorrectValues() {
        val sin = Sin()
        val file = tmpFile("test_vals_${System.nanoTime()}.csv")
        CsvWriter.writeToCsv(sin, 0.0, 0.0, 0.1, file.absolutePath)

        val lines = file.readLines()
        assertEquals(2, lines.size, "Заголовок + 1 строка данных")
        val (x, fx) = lines[1].split(",")
        assertEquals(0.0, x.toDouble(), 1e-10)
        assertEquals(0.0, fx.toDouble(), 1e-10, "sin(0) = 0")
    }
}

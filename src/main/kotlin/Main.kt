package org.example

import org.example.log.Ln
import org.example.trig.Sin
import kotlin.math.PI
import kotlin.math.E

fun main(args: Array<String>) {
    println("=== Система функций TPO Lab 2 ===\n")

    // Тест базовых функций
    val sin = Sin()
    val ln = Ln()

    println("Тестирование базовых функций:")
    println("sin(π/6) = ${sin.compute(PI / 6)} (ожидается ≈ 0.5)")
    println("sin(π/2) = ${sin.compute(PI / 2)} (ожидается ≈ 1.0)")
    println("ln(1) = ${ln.compute(1.0)} (ожидается 0.0)")
    println("ln(e) = ${ln.compute(E)} (ожидается ≈ 1.0)")
    println("ln(2) = ${ln.compute(2.0)} (ожидается ≈ 0.693)")
    println()

    // Вывод значений в CSV
    if (args.isNotEmpty()) {
        val x = args[0].toDoubleOrNull()
        if (x != null) {
            println("Вычисление f($x):")
            val system = SystemFunction()
            try {
                val result = system.compute(x)
                println("f($x) = $result")
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
            }
        }
    }

    // Пример: вывод sin в CSV
    println("\nВывод sin(x) в CSV:")
    val sinFunc = Sin()
    CsvWriter.writeToCsv(sinFunc, 0.0, PI, 0.1, "sin_output.csv")

    println("\nПроверка других модулей происходит через тестирование.")
}

package org.example

import org.example.log.Ln
import org.example.log.Log2
import org.example.log.Log3
import org.example.log.Log5
import org.example.log.Log10
import org.example.log.LogSystem
import org.example.trig.Cos
import org.example.trig.Csc
import org.example.trig.Sec
import org.example.trig.Sin
import org.example.trig.Tan
import org.example.trig.TrigSystem

/**
 * Использование:
 *   <модуль> <from> <to> <step> [выходной файл] [разделитель]
 *
 * Модули: sin, cos, tan, sec, csc, ln, log2, log3, log5, log10, trig, log, system
 *
 * Примеры:
 *   sin -1.57 1.57 0.1
 *   log 0.1 10.0 0.5 log_out.csv
 *   system -3.0 3.0 0.25 result.csv ";"
 *
 * Без аргументов: выводит все модули в CSV файлы с шагом по умолчанию.
 */
fun main(args: Array<String>) {
    val ln = Ln()
    val sin = Sin()

    val modules: Map<String, MathFunction> = mapOf(
        "sin"    to sin,
        "cos"    to Cos(sin),
        "tan"    to Tan(sin, Cos(sin)),
        "sec"    to Sec(Cos(sin)),
        "csc"    to Csc(sin),
        "ln"     to ln,
        "log2"   to Log2(ln),
        "log3"   to Log3(ln),
        "log5"   to Log5(ln),
        "log10"  to Log10(ln),
        "trig"   to TrigSystem(),
        "log"    to LogSystem(),
        "system" to SystemFunction()
    )

    if (args.isEmpty()) {
        println("Модули: ${modules.keys.joinToString()}")
        println("Вывод всех модулей в CSV файлы...")

        CsvWriter.writeToCsv(
            mapOf("sin" to modules["sin"]!!, "cos" to modules["cos"]!!,
                  "tan" to modules["tan"]!!, "sec" to modules["sec"]!!, "csc" to modules["csc"]!!),
            from = -Math.PI, to = -0.01, step = 0.1,
            filePath = "trig_output.csv"
        )

        CsvWriter.writeToCsv(
            mapOf("ln" to modules["ln"]!!, "log2" to modules["log2"]!!,
                  "log3" to modules["log3"]!!, "log5" to modules["log5"]!!, "log10" to modules["log10"]!!),
            from = 0.1, to = 10.0, step = 0.5,
            filePath = "log_output.csv"
        )

        CsvWriter.writeToCsv(modules["trig"]!!, -Math.PI, -0.01, 0.1, "trigsystem_output.csv")
        CsvWriter.writeToCsv(modules["log"]!!, 0.1, 10.0, 0.5, "logsystem_output.csv")
        CsvWriter.writeToCsv(modules["system"]!!, -Math.PI, 10.0, 0.25, "system_output.csv")
        return
    }

    if (args.size < 4) {
        System.err.println("Использование: <модуль> <from> <to> <step> [файл] [разделитель]")
        System.err.println("Модули: ${modules.keys.joinToString()}")
        return
    }

    val moduleName = args[0].lowercase()
    val module = modules[moduleName]
    if (module == null) {
        System.err.println("Неизвестный модуль: $moduleName")
        System.err.println("Доступные модули: ${modules.keys.joinToString()}")
        return
    }

    val from      = args[1].toDoubleOrNull() ?: run { System.err.println("Неверное значение from: ${args[1]}"); return }
    val to        = args[2].toDoubleOrNull() ?: run { System.err.println("Неверное значение to: ${args[2]}"); return }
    val step      = args[3].toDoubleOrNull() ?: run { System.err.println("Неверное значение step: ${args[3]}"); return }
    val filePath  = if (args.size > 4) args[4] else "${moduleName}_output.csv"
    val delimiter = if (args.size > 5) args[5] else ","

    CsvWriter.writeToCsv(module, from, to, step, filePath, delimiter)
}

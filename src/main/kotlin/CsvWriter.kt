package org.example

import java.io.File

/**
 * Вспомогательный класс для вывода значений функций в CSV
 */
object CsvWriter {

    fun writeToCsv(
        module: MathFunction,
        from: Double,
        to: Double,
        step: Double,
        filePath: String,
        delimiter: String = ","
    ) {
        require(step > 0) { "Шаг должен быть положительным" }
        require(from <= to) { "from должен быть <= to" }

        val file = File(filePath)
        file.parentFile?.mkdirs()

        file.bufferedWriter().use { writer ->
            // Заголовок
            writer.write("x${delimiter}f(x)\n")

            // Значения
            var x = from
            while (x <= to + step / 2) {
                try {
                    val value = module.compute(x)
                    writer.write("$x$delimiter$value\n")
                } catch (e: Exception) {
                    // Пропускаем недопустимые точки
                    writer.write("$x${delimiter}ERROR: ${e.message}\n")
                }
                x += step
            }
        }

        println("CSV файл сохранён: $filePath")
    }

    fun writeToCsv(
        modules: Map<String, MathFunction>,
        from: Double,
        to: Double,
        step: Double,
        filePath: String,
        delimiter: String = ","
    ) {
        require(step > 0) { "Шаг должен быть положительным" }
        require(from <= to) { "from должен быть <= to" }

        val file = File(filePath)
        file.parentFile?.mkdirs()

        file.bufferedWriter().use { writer ->
            // Заголовок
            writer.write("x")
            modules.forEach { (name, _) ->
                writer.write("$delimiter$name")
            }
            writer.write("\n")

            // Значения
            var x = from
            while (x <= to + step / 2) {
                writer.write(x.toString())
                modules.forEach { (_, module) ->
                    try {
                        val value = module.compute(x)
                        writer.write("$delimiter$value")
                    } catch (e: Exception) {
                        writer.write("${delimiter}ERROR")
                    }
                }
                writer.write("\n")
                x += step
            }
        }

        println("CSV файл сохранён: $filePath")
    }
}

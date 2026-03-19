package org.example

import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.XYChartBuilder
import java.io.File

object PlotCsv {

    @JvmStatic
    fun main(args: Array<String>) {
        val inputDir = File(if (args.isNotEmpty()) args[0] else "plots")
        val outputDir = File(if (args.size > 1) args[1] else "plots_out")
        outputDir.mkdirs()

        val configs = listOf(
            PlotConfig("sin", File(inputDir, "sin.csv"), maxAbsY = null),
            PlotConfig("cos", File(inputDir, "cos.csv"), maxAbsY = null),
            PlotConfig("tan", File(inputDir, "tan.csv"), maxAbsY = 100.0),
            PlotConfig("sec", File(inputDir, "sec.csv"), maxAbsY = 100.0),
            PlotConfig("csc", File(inputDir, "csc.csv"), maxAbsY = 100.0),

            PlotConfig("ln", File(inputDir, "ln.csv"), maxAbsY = null),
            PlotConfig("log2", File(inputDir, "log2.csv"), maxAbsY = null),
            PlotConfig("log3", File(inputDir, "log3.csv"), maxAbsY = null),
            PlotConfig("log5", File(inputDir, "log5.csv"), maxAbsY = null),
            PlotConfig("log10", File(inputDir, "log10.csv"), maxAbsY = null),

            PlotConfig("trigsystem", File(inputDir, "trigsystem.csv"), maxAbsY = 100.0),
            PlotConfig("logsystem", File(inputDir, "logsystem.csv"), maxAbsY = null),
            PlotConfig("system", File(inputDir, "system.csv"), maxAbsY = 100.0),
        )

        configs.forEach { config ->
            if (!config.csv.exists()) {
                println("Пропуск: ${config.csv.absolutePath} не найден")
                return@forEach
            }

            val points = readPoints(config.csv, maxAbsY = config.maxAbsY)
            if (points.isEmpty()) {
                println("Пропуск: ${config.csv.name} не содержит валидных точек")
                return@forEach
            }

            saveChart(
                title = config.title,
                xs = points.map { it.first },
                ys = points.map { it.second },
                outputFile = File(outputDir, "${config.title}.png")
            )
        }

        println("Графики сохранены в: ${outputDir.absolutePath}")
    }

    data class PlotConfig(
        val title: String,
        val csv: File,
        val maxAbsY: Double? = null
    )

    private fun readPoints(csv: File, maxAbsY: Double? = null): List<Pair<Double, Double>> {
        return csv.readLines()
            .drop(1)
            .mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size < 2) return@mapNotNull null

                val x = parts[0].trim().toDoubleOrNull() ?: return@mapNotNull null
                val yRaw = parts[1].trim()

                if (yRaw.startsWith("ERROR")) return@mapNotNull null

                val y = yRaw.toDoubleOrNull() ?: return@mapNotNull null
                if (y.isNaN() || y.isInfinite()) return@mapNotNull null

                if (maxAbsY != null && kotlin.math.abs(y) > maxAbsY) return@mapNotNull null

                x to y
            }
    }

    private fun saveChart(
        title: String,
        xs: List<Double>,
        ys: List<Double>,
        outputFile: File
    ) {
        val chart = XYChartBuilder()
            .width(1000)
            .height(700)
            .title(title)
            .xAxisTitle("x")
            .yAxisTitle("y")
            .build()

        chart.addSeries(title, xs, ys)

        outputFile.parentFile?.mkdirs()
        BitmapEncoder.saveBitmap(chart, outputFile.absolutePath.removeSuffix(".png"), BitmapEncoder.BitmapFormat.PNG)
        println("Сохранён график: ${outputFile.absolutePath}")
    }
}
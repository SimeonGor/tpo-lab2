package org.example

import org.example.log.LogSystem
import org.example.trig.TrigSystem

/**
 * Главная система функций с маршрутизацией по x
 * x <= 0: тригонометрическая ветка
 * x > 0: логарифмическая ветка
 */
class SystemFunction(
    val trigSystem: TrigSystem = TrigSystem(),
    val logSystem: LogSystem = LogSystem()
) : MathFunction {

    override fun compute(x: Double): Double {
        return when {
            x <= 0.0 -> {
                try {
                    trigSystem.compute(x)
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException("TrigSystem ошибка при x=$x: ${e.message}")
                }
            }
            x > 0.0 -> {
                try {
                    logSystem.compute(x)
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException("LogSystem ошибка при x=$x: ${e.message}")
                }
            }
            else -> throw IllegalArgumentException("Неожиданное значение x=$x")
        }
    }
}

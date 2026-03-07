package org.example

/**
 * Интерфейс для математической функции с заданной точностью
 */
interface MathFunction {
    fun compute(x: Double): Double
}

/**
 * Значение функции с информацией об ошибке
 */
data class FunctionValue(
    val x: Double,
    val value: Double,
    val isUndefined: Boolean = false,
    val error: String? = null
)

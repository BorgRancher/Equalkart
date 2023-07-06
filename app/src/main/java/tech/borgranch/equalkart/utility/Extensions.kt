package tech.borgranch.equalkart.utility

import java.math.BigDecimal
import java.math.RoundingMode

fun String.toSnakeCase(): String {
    return this.trim().lowercase().replace(" ", "_")
}

fun Double.roundToCurrency(decimals: Int = 2): Double {
    return BigDecimal(this).setScale(decimals, RoundingMode.HALF_UP).toDouble()
}

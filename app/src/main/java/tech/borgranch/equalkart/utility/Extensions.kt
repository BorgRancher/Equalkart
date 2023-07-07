package tech.borgranch.equalkart.utility

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale

fun String.toSnakeCase(): String {
    return this.trim().lowercase().replace(" ", "_")
}

fun Double.roundToCurrency(decimals: Int = 2): Double {
    return BigDecimal(this).setScale(decimals, RoundingMode.HALF_UP).toDouble()
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
    it.lowercase().replaceFirstChar { e ->
        e.uppercase(
            Locale.getDefault(),
        )
    }
}

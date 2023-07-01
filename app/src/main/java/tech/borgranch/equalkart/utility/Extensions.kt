package tech.borgranch.equalkart.utility

fun String.toSnakeCase(): String {
    return this.trim().lowercase().replace(" ", "_")
}

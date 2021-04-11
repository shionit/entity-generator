package misc

/**
 * Generator logic utilities.
 */
class GeneratorUtils {
}

fun String.toUpperCamelCase(): String {
    return this.split('_').map {
        when (it.length) {
            0 -> ""
            1 -> it.toUpperCase()
            else -> it[0].toUpperCase() + it.substring(1).toLowerCase()
        }
    }.joinToString("")
}

fun String.toLowerCamelCase(): String {
    return this.split('_').map {
        when (it.length) {
            0 -> ""
            1 -> it.toUpperCase()
            else -> it[0].toUpperCase() + it.substring(1).toLowerCase()
        }
    }.joinToString("").decapitalize()
}

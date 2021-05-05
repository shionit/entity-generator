package model

import java.time.LocalDate

/**
 * Generate target class has to implement this.
 */
interface GenerateTarget {
    fun year(): String {
        val now = LocalDate.now()
        return "${now.year}"
    }
}

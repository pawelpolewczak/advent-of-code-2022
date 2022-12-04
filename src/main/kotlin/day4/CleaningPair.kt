package day4

import utils.Utils

class CleaningPair(val firstArea: Set<Int>, val secondArea: Set<Int>) {

    fun areFullOverlapping(): Boolean {
        return firstArea.containsAll(secondArea) || secondArea.containsAll(firstArea)
    }

    fun arePartiallyOverlapping(): Boolean {
        return firstArea.any{ it in secondArea }
    }

    companion object {
        fun fromLine(line: String): CleaningPair {
            return line.split(',')
                .map(Companion::stringRangeToSet)
                .zipWithNext()
                .map { pair -> CleaningPair(pair.first, pair.second) }
                .first()
        }

        private fun stringRangeToSet(line: String): Set<Int> {
            return line.split('-')
                .map(String::toInt)
                .zipWithNext()
                .map { (it.first..it.second).toSet() }
                .first()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CleaningPair

        if (firstArea != other.firstArea) return false
        if (secondArea != other.secondArea) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstArea.hashCode()
        result = 31 * result + secondArea.hashCode()
        return result
    }

    override fun toString(): String {
        return "CleaningPair(firstArea=$firstArea, secondArea=$secondArea)"
    }
}

fun main() {
    val inputLines = Utils.readFileAsListOfLines("/day4/input.txt")
    val fullOverlappingCount = inputLines.map(CleaningPair.Companion::fromLine)
        .count(CleaningPair::areFullOverlapping)
    val partiallyOverlappingCount = inputLines.map(CleaningPair.Companion::fromLine)
        .count(CleaningPair::arePartiallyOverlapping)
    println("Full Overlapping areas: $fullOverlappingCount\n")
    println("Partially Overlapping areas: $partiallyOverlappingCount\n")
}

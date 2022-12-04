package day3

import utils.Utils

class Rucksack(val firstCompartment: String, val secondCompartment: String) {
    fun getSharedItems(): Set<Char> {
        return firstCompartment.toCharArray()
            .intersect(secondCompartment.toCharArray().asIterable().toSet())
    }

    fun getWholeEquipmentAsString(): String {
        return firstCompartment.plus(secondCompartment)
    }

    companion object {
        fun fromLine(line: String): Rucksack {
            val compartments = line.chunked(line.length / 2).foldIndexed(Pair("", ""))
            { index, resultPair, compartment ->
                when (index) {
                    0 -> Pair(compartment, resultPair.second)
                    1 -> Pair(resultPair.first, compartment)
                    else -> Pair(resultPair.first, resultPair.second)
                }
            }
            return Rucksack(compartments.first, compartments.second)
        }

        fun calculateItemPriority(input: Char): Int {
            return if (input !in 'a'..'z' && input !in 'A'..'Z') 0
            else
                when (input.isLowerCase()) {
                    true -> (input - 'a') + 1
                    false -> (input - 'A') + 27
                }
        }

        fun findBadgeFromGroupOfRucksacks(rucksacks: List<Rucksack>): Char {
            return rucksacks.fold(setOf<Char>()){
                acc, rucksack -> when(acc.isEmpty()) {
                    true -> rucksack.getWholeEquipmentAsString().toCharArray().asIterable().toSet()
                    false -> acc.intersect(rucksack.getWholeEquipmentAsString().toCharArray().asIterable().toSet())
                }
            }.first()
        }
    }
}

fun main() {
    val linesOfRucksacks = Utils.readFileAsListOfLines("/day3/input.txt")
    val sumOfPriorities = linesOfRucksacks.map(Rucksack.Companion::fromLine)
        .map(Rucksack::getSharedItems)
        .map(Set<Char>::first)
        .map(Rucksack.Companion::calculateItemPriority)
        .sumOf { it }
    println("Sum of priorities is: $sumOfPriorities")

    val sumOfPrioritiesForBadges = linesOfRucksacks.map(Rucksack.Companion::fromLine)
        .chunked(3)
        .map(Rucksack.Companion::findBadgeFromGroupOfRucksacks)
        .map(Rucksack.Companion::calculateItemPriority)
        .sumOf { it }

    println("Sum of priorities for badges is: $sumOfPrioritiesForBadges")
}

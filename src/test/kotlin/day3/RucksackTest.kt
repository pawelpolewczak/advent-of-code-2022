package day3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class RucksackTest {

    @TestFactory
    fun `rucksack should be created with correctly assigned compartments`() =
        listOf(
            "abcd" to Pair("ab", "cd"),
            "vJrwpWtwJgWrhcsFMMfFFhFp" to Pair("vJrwpWtwJgWr", "hcsFMMfFFhFp"),
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL" to Pair("jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL"),
            "PmmdzqPrVvPwwTWBwg" to Pair("PmmdzqPrV", "vPwwTWBwg"),
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Givem input $input" +
                        "when creating the rucksack object" +
                        "should result with correctly assigned compartments $expected"
            ) {
                val result = Rucksack.fromLine(input)
                assertEquals(expected.first, result.firstCompartment)
                assertEquals(expected.second, result.secondCompartment)
            }
        }

    @TestFactory
    fun `rucksack should detect shared items in two compartments`() =
        listOf(
            "abcd" to emptySet(),
            "vJrwpWtwJgWrhcsFMMfFFhFp" to setOf('p'),
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL" to setOf('L'),
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn" to setOf('v'),
            "ttgJtRGJQctTZtZT" to setOf('t'),
            "CrZsJsPPZsGzwwsLwLmpwMDw" to setOf('s'),
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Givem input $input" +
                        "when fetching shared items between compartments" +
                        "should result with $expected"
            ) {
                val result = Rucksack.fromLine(input).getSharedItems()
                assertIterableEquals(expected, result)
            }
        }

    @TestFactory
    fun `rucksack should calculate correctly item priority`() =
        listOf(
            'a' to 1,
            'z' to 26,
            'A' to 27,
            'Z' to 52,
            'p' to 16,
            'L' to 38,
            'P' to 42,
            'v' to 22,
            't' to 20,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Givem input $input" +
                        "when calucating item priority" +
                        "should result with $expected"
            ) {
                assertEquals(expected, Rucksack.calculateItemPriority(input))
            }
        }

    @TestFactory
    fun `it should find correct badge from list of rucksacks`() =
        listOf(
            listOf("abcd", "zzza", "xqwa") to 'a',
            listOf("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg") to 'r',
            listOf("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw") to 'Z'
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Givem input $input" +
                        " when finding badge" +
                        " should result with $expected"
            ) {
                val rucksacks = input.map(Rucksack.Companion::fromLine)
                val result = Rucksack.findBadgeFromGroupOfRucksacks(rucksacks)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `it should return full equipment from rucksack`() =
        listOf(
            Rucksack("ab", "cd") to "abcd",
            Rucksack("vJrwpWtwJgWr", "hcsFMMfFFhFp") to "vJrwpWtwJgWrhcsFMMfFFhFp",
            Rucksack("jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL") to "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            Rucksack("PmmdzqPrV", "vPwwTWBwg") to "PmmdzqPrVvPwwTWBwg",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Givem input $input" +
                        " when fetching whole equipment" +
                        " should result with $expected"
            ) {
                assertEquals(expected, input.getWholeEquipmentAsString())
            }
        }
}

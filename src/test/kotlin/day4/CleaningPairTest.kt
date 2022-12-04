package day4

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

internal class CleaningPairTest {

    @TestFactory
    fun `it should create CleaningPair from line`() =
        listOf(
            "2-4,6-8" to CleaningPair(setOf(2, 3, 4), setOf(6, 7, 8)),
            "1-2,3-4" to CleaningPair(setOf(1, 2), setOf(3, 4)),
            "1-1,2-2" to CleaningPair(setOf(1), setOf(2))
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Given line $input" +
                        " when creating object using \"fromLine(line)\"" +
                        " should result with: $expected"
            )
            {
                val result = CleaningPair.fromLine(input)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `it should detect if cleaning pair is fully overlapping each other`() =
        listOf(
            "2-4,6-8" to false,
            "1-2,3-4" to false,
            "1-1,2-2" to false,
            "1-2,1-2" to true,
            "1-10,8-9" to true,
            "1-5,4-8" to false
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Given line $input" +
                        " when checking if areas are overlapping" +
                        " should result with: $expected"
            )
            {
                val result = CleaningPair.fromLine(input).areFullOverlapping()
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `it should detect if cleaning pair is at least partially overlapping each other`() =
        listOf(
            "2-4,6-8" to false,
            "1-2,3-4" to false,
            "1-1,2-2" to false,
            "1-2,1-2" to true,
            "1-10,8-9" to true,
            "1-5,4-8" to true,
            "2-5,3-8" to true
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Given line $input" +
                        " when checking if areas are overlapping" +
                        " should result with: $expected"
            )
            {
                val result = CleaningPair.fromLine(input).arePartiallyOverlapping()
                assertEquals(expected, result)
            }
        }

}

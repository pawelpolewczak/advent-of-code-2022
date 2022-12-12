package day8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import utils.Utils

internal class TreeGridTest {

    @Test
    fun `it should correctly create TreeGrid object from lines of String`() {
        //given
        val input = listOf(
            "1234",
            "1234",
            "1234",
            "1234"
        )

        //when
        val result = TreeGrid.fromLines(input)

        //then
        assertNotNull(result)
        assertEquals(16, result.getNumberOfTrees())
    }

    @Test
    fun `it should correctly count trees that are visible outside the grid`() {
        //given
        val input = listOf(
            "5555",
            "5624",
            "2374",
            "4262"
        )

        //when
        val result = TreeGrid.fromLines(input).getNumberOfVisibleTrees()

        //then
        assertEquals(15, result)
    }

    @Test
    fun `it should correctly count trees that are visible outside the grid from file input`() {
        //given
        val input = getInputFromFile()

        //when
        val result = TreeGrid.fromLines(input).getNumberOfVisibleTrees()

        //then
        assertEquals(1812, result)
    }

    @TestFactory
    fun `it should correctly calculate sonic score for each tree`() =
        listOf(
            Pair(1, 2) to 4,
            Pair(3, 2) to 8
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("Given input: $input" +
                    " when calculating sonic score" +
                    " should result in $expected") {
                //given
                val grid = listOf(
                    "30373",
                    "25512",
                    "65332",
                    "33549",
                    "35390"
                )

                //when
                val result = TreeGrid.fromLines(grid).calculateScenicScore(input.first, input.second)

                //then
                assertEquals(expected, result)
            }
        }

    @Test
    fun `it should correctly calculate sonic score for each tree from file input`() {
        //given
        val input = getInputFromFile()

        //when
        val result = TreeGrid.fromLines(input).getAllTreesWithTheirScenicScore().maxByOrNull { it.second }

        //then
        assertEquals(315495, result!!.second)
    }

    private fun getInputFromFile() = Utils.readFileAsListOfLines("/day8/input.txt")
}

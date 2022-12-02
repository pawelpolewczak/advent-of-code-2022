package day2

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class RockPaperScissorsTest {

    @TestFactory
    fun `given input first column marker, correct game choice should be received`() =
        getCorrectArgumentsForFirstColumnCheck().map { (value, expected) ->
            DynamicTest.dynamicTest(
                "given $value when " +
                        "fetching GameChoice " +
                        "should return ${expected.name}"
            ) {
                val result = RockPaperScissors.GameChoice.fromFirstColumn(value)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given input second column marker, correct game choice should be received`() =
        getCorrectArgumentsForSecondColumnCheck().map { (value, expected) ->
            DynamicTest.dynamicTest(
                "given $value when " +
                        "fetching GameChoice " +
                        "should return ${expected.name}"
            ) {
                val result = RockPaperScissors.GameChoice.fromSecondColumn(value)
                assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given unknown input for first column marker, should result in exception`() =
        getCorrectArgumentsForSecondColumnCheck().map { (value) ->
            DynamicTest.dynamicTest(
                "given $value when " +
                        "fetching GameChoice from first column" +
                        "should result in exception"
            ) {
                assertThrows<java.util.NoSuchElementException> { RockPaperScissors.GameChoice.fromFirstColumn(value) }
            }
        }

    @TestFactory
    fun `given unknown input for second column marker, should result in exception`() =
        getCorrectArgumentsForFirstColumnCheck().map { (value) ->
            DynamicTest.dynamicTest(
                "given $value when " +
                        "fetching GameChoice from second column " +
                        "should result in exception"
            ) {
                assertThrows<java.util.NoSuchElementException> { RockPaperScissors.GameChoice.fromSecondColumn(value) }
            }
        }

    @TestFactory
    fun `given inputs should result with correct game result`() =
        getArgumentsForCalculatingResult().map { (pair, expected) ->
            DynamicTest.dynamicTest(
                "given pair of inputs $pair" +
                        "when calculating the game resul" +
                        "should result in $expected"
            ) {
                val result = RockPaperScissors.GameChoice.resultForSecond(pair)
                assertEquals(result, expected)
            }
        }

    @TestFactory
    fun `given input should result with correct game choice based on expected result`() =
        getArgumentsForChoosingGameChoice().map { (pair, expected) ->
            DynamicTest.dynamicTest(
                "given pair of inputs $pair" +
                        "when calculating the game choice" +
                        "should result in $expected"
            ) {
                val result = RockPaperScissors.ResultType.getGameChoiceBasedOnExpectedResult(pair.first, pair.second)
                assertEquals(result, expected)
            }
        }

    @TestFactory
    fun `given input result column marker, correct result should be received`() =
        getCorrectArgumentsForResultTypeMarker().map { (value, expected) ->
            DynamicTest.dynamicTest(
                "given $value when " +
                        "fetching result type " +
                        "should return ${expected.name}"
            ) {
                val result = RockPaperScissors.ResultType.fromMarker(value)
                assertEquals(expected, result)
            }
        }


    private fun getCorrectArgumentsForFirstColumnCheck() = listOf(
        "A" to RockPaperScissors.GameChoice.ROCK,
        "B" to RockPaperScissors.GameChoice.PAPER,
        "C" to RockPaperScissors.GameChoice.SCISSORS
    )

    private fun getCorrectArgumentsForSecondColumnCheck() = listOf(
        "X" to RockPaperScissors.GameChoice.ROCK,
        "Y" to RockPaperScissors.GameChoice.PAPER,
        "Z" to RockPaperScissors.GameChoice.SCISSORS
    )

    private fun getArgumentsForCalculatingResult() = listOf(
        Pair(RockPaperScissors.GameChoice.ROCK, RockPaperScissors.GameChoice.ROCK) to RockPaperScissors.GameResult(
            RockPaperScissors.ResultType.DRAW,
            4
        ),
        Pair(RockPaperScissors.GameChoice.ROCK, RockPaperScissors.GameChoice.SCISSORS) to RockPaperScissors.GameResult(
            RockPaperScissors.ResultType.LOSE,
            3
        ),
        Pair(RockPaperScissors.GameChoice.ROCK, RockPaperScissors.GameChoice.PAPER) to RockPaperScissors.GameResult(
            RockPaperScissors.ResultType.WIN,
            8
        ),
        Pair(RockPaperScissors.GameChoice.PAPER, RockPaperScissors.GameChoice.PAPER) to RockPaperScissors.GameResult(
            RockPaperScissors.ResultType.DRAW,
            5
        ),
        Pair(RockPaperScissors.GameChoice.SCISSORS, RockPaperScissors.GameChoice.ROCK) to RockPaperScissors.GameResult(
            RockPaperScissors.ResultType.WIN,
            7
        )
    )

    private fun getArgumentsForChoosingGameChoice() = listOf(
        Pair(RockPaperScissors.GameChoice.ROCK, RockPaperScissors.ResultType.DRAW) to RockPaperScissors.GameChoice.ROCK,
        Pair(RockPaperScissors.GameChoice.ROCK, RockPaperScissors.ResultType.LOSE) to RockPaperScissors.GameChoice.SCISSORS,
        Pair(RockPaperScissors.GameChoice.ROCK, RockPaperScissors.ResultType.WIN) to RockPaperScissors.GameChoice.PAPER,
        Pair(RockPaperScissors.GameChoice.PAPER, RockPaperScissors.ResultType.WIN) to RockPaperScissors.GameChoice.SCISSORS,
    )

    private fun getCorrectArgumentsForResultTypeMarker() = listOf(
        "X" to RockPaperScissors.ResultType.LOSE,
        "Y" to RockPaperScissors.ResultType.DRAW,
        "Z" to RockPaperScissors.ResultType.WIN
    )
}

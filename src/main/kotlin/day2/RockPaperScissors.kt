package day2

class RockPaperScissors {

    enum class GameChoice(val score: Int, val firstColumnMarker: Char, val secondColumnMarker: Char) {
        ROCK(1, 'A', 'X') {
            override fun winsWith(): GameChoice = SCISSORS
        },
        PAPER(2, 'B', 'Y') {
            override fun winsWith(): GameChoice = ROCK
        },
        SCISSORS(3, 'C', 'Z') {
            override fun winsWith(): GameChoice = PAPER
        };

        abstract fun winsWith(): GameChoice

        companion object {
            fun resultForSecond(pair: Pair<GameChoice, GameChoice>): GameResult {
                val result = calculateResult(pair)
                return GameResult(result, pair.second.score + result.score)
            }

            private fun calculateResult(pair: Pair<GameChoice, GameChoice>) = if (pair.first == pair.second) {
                ResultType.DRAW
            } else if (pair.first.winsWith() == pair.second) {
                ResultType.LOSE
            } else ResultType.WIN

            fun fromFirstColumn(marker: String): GameChoice {
                return getByProperty(marker, GameChoice::firstColumnMarker)
            }

            fun fromSecondColumn(marker: String): GameChoice {
                return getByProperty(marker, GameChoice::secondColumnMarker)
            }

            private fun getByProperty(
                marker: String,
                property: java.util.function.Function<GameChoice, Char>
            ): GameChoice {
                val markerChar = marker.single()
                return values().first { choice -> property.apply(choice) == markerChar }
            }
        }
    }

    enum class ResultType(val score: Int, val columnMarker: Char) {
        WIN(6, 'Z'),
        DRAW(3, 'Y'),
        LOSE(0, 'X');

        companion object {
            fun getGameChoiceBasedOnExpectedResult(opponent: GameChoice, expectedResult: ResultType): GameChoice {
                return when (expectedResult) {
                    DRAW -> opponent
                    LOSE -> opponent.winsWith()
                    else -> GameChoice.values().single { it !in listOf(opponent, opponent.winsWith()) }
                }
            }

            fun fromMarker(marker: String): ResultType {
                val markerChar = marker.single()
                return values().first { it.columnMarker == markerChar }
            }
        }
    }

    data class GameResult(val result: ResultType, val score: Int)
}

fun main() {
    val linesOfPredictions = object {}.javaClass.getResourceAsStream("/day2/input.txt")?.bufferedReader()?.readLines()!!
    val result = linesOfPredictions
        .map { line -> line.split(' ') }

        .map { lineAsList ->
            Pair(
                RockPaperScissors.GameChoice.fromFirstColumn(lineAsList[0]),
                RockPaperScissors.GameChoice.fromSecondColumn(lineAsList[1])
            )
        }
        .map { pair -> RockPaperScissors.GameChoice.resultForSecond(pair) }
        .sumOf { it.score }
    println("Result of first part: $result")

    val secondResult = linesOfPredictions
        .map { line -> line.split(' ') }

        .map { lineAsList ->
            Pair(
                RockPaperScissors.GameChoice.fromFirstColumn(lineAsList[0]),
                RockPaperScissors.ResultType.getGameChoiceBasedOnExpectedResult(RockPaperScissors.GameChoice.fromFirstColumn(lineAsList[0]),
                    RockPaperScissors.ResultType.fromMarker(lineAsList[1]))
            )
        }
        .map { pair -> RockPaperScissors.GameChoice.resultForSecond(pair) }
        .sumOf { it.score }
    println("Result of second part: $secondResult")

}

package day6

import utils.Utils

class SignalData(val input: String) {

    fun detectMarkerPosition(): Int {
        return detectDistinctCharacters(4)
    }

    fun detectMessageMarkerPosition(): Int {
        return detectDistinctCharacters(14)
    }

    private fun detectDistinctCharacters(count: Int): Int {
        return input.foldIndexed(listOf<Char>())
        { index, list, character ->
            when (hasEnoughDistinctCharacters(list, count)) {
                true -> list
                false -> list + character
            }
        }.size

    }

    private fun hasEnoughDistinctCharacters(list: List<Char>, count: Int) =
        list.size >= count &&
                list.subList(list.size - count, list.size).toSet().size == count

}

fun main() {
    val inputLine = Utils.readFileAsListOfLines("/day6/input.txt").first()
    val signalData = SignalData(inputLine)
    val part1Result = signalData.detectMarkerPosition()
    val part2Result = signalData.detectMessageMarkerPosition()
    println("Part 1 result: $part1Result\nPart 2 result: $part2Result")
}

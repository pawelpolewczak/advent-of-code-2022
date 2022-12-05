package day5

import utils.Utils
import java.util.*

class Ship(val stacks: List<Stack<Char>>, val instructions: List<MoveInstruction>) {

    fun performInstructions() {
        instructions.forEach(::move)
    }

    fun performInstructionsWithNewVersion() {
        instructions.forEach(::moveManyOrdered)
    }

    private fun moveManyOrdered(instruction: MoveInstruction) {
        val tempList = mutableListOf<Char>()
        for (i in 1..instruction.howMany) {
            val temp = stacks[instruction.from - 1].pop()
            tempList.add(temp)
        }
        tempList.reversed().forEach { stacks[instruction.to - 1].push(it) }
    }

    private fun move(instruction: MoveInstruction) {
        for (i in 1..instruction.howMany) {
            val temp = stacks[instruction.from - 1].pop()
            stacks[instruction.to - 1].push(temp)
        }
    }

    fun getAllAtTop(): String {
        return stacks.map { it.peek() }.joinToString("")
    }

    companion object {
        fun parseFrom(inputLines: List<String>): Ship {
            val initialStateMatrix = inputLines.takeWhile { line -> line.isNotEmpty() }
            val numberOfStacks = initialStateMatrix.last().split(' ').filter { it.isNotEmpty() }.size
            val stacks = List(numberOfStacks) { Stack<Char>() }

            initialStateMatrix.reversed().subList(1, numberOfStacks).forEach {
                it.chunked(4).forEachIndexed { index, line ->
                    if (line.isNotBlank()) stacks[index].push(line.find { it.isLetter() })
                }
            }

            val instructions = inputLines.subList(initialStateMatrix.size + 1, inputLines.size)
                .map(MoveInstruction.Companion::fromLine)
            return Ship(stacks, instructions)
        }
    }

    class MoveInstruction(val howMany: Int, val from: Int, val to: Int) {

        companion object {
            fun fromLine(line: String): MoveInstruction {
                return Regex("[0-9]+").findAll(line).map(MatchResult::value)
                    .foldIndexed(MoveInstruction(0, 0, 0)) { index, acc, s ->
                        when (index) {
                            0 -> MoveInstruction(s.toInt(), acc.from, acc.to)
                            1 -> MoveInstruction(acc.howMany, s.toInt(), acc.to)
                            2 -> MoveInstruction(acc.howMany, acc.from, s.toInt())
                            else -> acc
                        }
                    }
            }
        }

        override fun toString(): String {
            return "MoveInstruction(howMany=$howMany, from=$from, to=$to)"
        }
    }
}

fun main() {
    val input = Utils.readFileAsListOfLines("/day5/input.txt")

    val ship1 = Ship.parseFrom(input)
    val ship2 = Ship.parseFrom(input)
    ship1.performInstructions()
    ship2.performInstructionsWithNewVersion()

    //then
    println("Result of part 1: ${ship1.getAllAtTop()}, result of part 2: ${ship2.getAllAtTop()}")
}


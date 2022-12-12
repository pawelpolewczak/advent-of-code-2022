package day8

import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.IntStream

class TreeGrid(val trees: Array<IntArray>) {

    fun getNumberOfTrees(): Int {
        return trees.sumOf { it.count() }
    }

    fun getNumberOfVisibleTrees(): Int {
        var visibleTrees = 0
        for (i in trees.indices) {
            for (j in trees[i].indices) {
                if (visibleHorizontal(i, j) || visibleVertical(i, j)) {
                    visibleTrees++
                }
            }
        }
        return visibleTrees
    }

    fun getAllTreesWithTheirScenicScore(): MutableList<Pair<Pair<Int, Int>, Int>> {
        val result = mutableListOf<Pair<Pair<Int, Int>, Int>>()
        for (i in trees.indices) {
            for (j in trees[i].indices) {
                result.add(Pair(Pair(i, j), calculateScenicScore(i, j)))
            }
        }
        return result
    }

    fun calculateScenicScore(y: Int, x: Int): Int {
        return calculateScenicScoreHorizontally(y, x) * calculateScenicScoreVertically(y, x)
    }

    private fun calculateScenicScoreVertically(y: Int, x: Int): Int {
        val flippedArray = IntStream.range(0, trees.size).map { trees[it][x] }.toArray()
        return calculatePathsToEdge(flippedArray, y)
    }

    private fun calculateScenicScoreHorizontally(y: Int, x: Int): Int {
        return calculatePathsToEdge(trees[y], x)
    }

    private fun calculatePathsToEdge(array: IntArray, y: Int): Int {
        val toUp = array.slice(0 until y).foldRight(
            IncrementationResult())
            { i, acc ->
                when (i.compareTo(array[y])) {
                    1 -> acc.incrementAndStop()
                    0 ->  acc.incrementAndStop()
                    else -> acc.increment()
                }
            }
            .getResult()
        val toDown = array.slice(y + 1 until array.size).fold(
            IncrementationResult())
        { acc, i ->
            when (i.compareTo(array[y])) {
                1 -> acc.incrementAndStop()
                0 ->  acc.incrementAndStop()
                else -> acc.increment()
            }
        }
            .getResult()
        return toUp * toDown
    }

    private fun visibleVertical(y: Int, x: Int): Boolean {
        if (x == 0 || x == trees.get(y).size - 1) return true
        val treeValue = trees.get(y).get(x)
        return allLesserVertically(0, y, treeValue, x) ||
                allLesserVertically(y + 1, trees.size, treeValue, x)
    }

    private fun visibleHorizontal(y: Int, x: Int): Boolean {
        if (y == 0 || y == trees.size - 1) return true
        val treeValue = trees.get(y).get(x)
        return allLesserHorizontally(0, x, treeValue, y) ||
                allLesserHorizontally(x + 1, trees[y].size, treeValue, y)
    }

    private fun allLesserHorizontally(indexFrom: Int, indexTo: Int, value: Int, y: Int): Boolean {
        return IntStream.range(indexFrom, indexTo)
            .map { trees[y][it] }
            .allMatch { it < value }
    }

    private fun allLesserVertically(indexFrom: Int, indexTo: Int, value: Int, x: Int): Boolean {
        return IntStream.range(indexFrom, indexTo)
            .map { trees[it][x] }
            .allMatch { it < value }
    }


    companion object {
        fun fromLines(lines: List<String>): TreeGrid {
            return TreeGrid(
                lines.map {
                    it.split("")
                        .filter { it.isNotEmpty() }
                        .map { it.toInt() }
                        .toIntArray()
                }.toTypedArray()
            )
        }

    }

    internal class IncrementationResult(private val counter: AtomicInteger = AtomicInteger(0),
                                        private var stopCounting: Boolean = false) {

        fun increment(): IncrementationResult {
            if (!stopCounting) {
                counter.incrementAndGet()
            }
            return this
        }

        fun incrementAndStop(): IncrementationResult {
            increment()
            this.stopCounting = true
            return this
        }

        fun getResult(): Int {
            return counter.get()
        }
    }
}

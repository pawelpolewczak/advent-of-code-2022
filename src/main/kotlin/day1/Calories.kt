package day1

class Calories constructor(inputFilePath: String) {

    private val linesOfCalories: List<String> = this::class.java.getResourceAsStream(inputFilePath)?.bufferedReader()?.readLines()!!
    private val accumulatedCalories: MutableSet<Int> = mutableSetOf()

    fun getResult(): CaloriesResult {
        var accumulator = 0
        for (calories in linesOfCalories) {
            val caloriesAsInt = calories.toIntOrNull()
            if (caloriesAsInt == null) {
                accumulatedCalories.add(accumulator)
                accumulator = 0
            } else accumulator += caloriesAsInt
        }

        val maxNumberOfCaloriesCarried = accumulatedCalories.stream().max(Int::compareTo).orElse(0)
        val sumOfTopThreeCaloriesCarried = accumulatedCalories.stream()
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .reduce(Int::plus)
            .orElse(0)

        return CaloriesResult(maxNumberOfCaloriesCarried, sumOfTopThreeCaloriesCarried)
    }


    class CaloriesResult(val maxNumberOfCaloriesCarried: Int, val sumOfTopThreeCaloriesCarried: Int)

}

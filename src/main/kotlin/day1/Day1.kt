package day1


fun main() {

    val result = Calories("/day1/input.txt").getResult()
    println("Maximum number of calories is ${result.maxNumberOfCaloriesCarried}, and top three calories carried are: ${result.sumOfTopThreeCaloriesCarried}")
}



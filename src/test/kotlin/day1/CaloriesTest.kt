package day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class CaloriesTest {

    @Test
    fun testIfResultFromFileIsCalculatedCorrectly() {
        val result = Calories("/day1/input.txt").getResult()
        assertEquals(70116, result.maxNumberOfCaloriesCarried)
        assertEquals(206582, result.sumOfTopThreeCaloriesCarried)
    }
}

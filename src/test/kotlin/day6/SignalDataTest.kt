package day6

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


internal class SignalDataTest {

    @TestFactory
    fun `it should detect correct marker position for given input`() =
        listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 7,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
            "nppdvjthqldpwncqszvftbrmjlhg" to 6,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "When given input is passed: $input" +
                        " then it should detect marker at position: $expected"
            )
            {
                assertEquals(expected, SignalData(input).detectMarkerPosition())
            }
        }

    @TestFactory
    fun `it should detect correct message marker position for given input`() =
        listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
            "nppdvjthqldpwncqszvftbrmjlhg" to 23,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "When given input is passed: $input" +
                        " then it should detect message marker at position: $expected"
            )
            {
                assertEquals(expected, SignalData(input).detectMessageMarkerPosition())
            }
        }
}




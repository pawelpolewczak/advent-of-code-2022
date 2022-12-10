package day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class CommandTypeTypeParserTest {

    @TestFactory
    fun `it should correctly parse given line to command`() =
        listOf(
            "$ ls" to Command.CommandType.LIST_ITEMS,
            "$ cd a" to Command.CommandType.CHANGE_DIRECTORY,
            "$ cd x" to Command.CommandType.CHANGE_DIRECTORY,
            "$ cd .." to Command.CommandType.CHANGE_DIRECTORY,
            "$ dir" to Command.CommandType.UNDEFINED,
            "dir a" to Command.CommandType.UNDEFINED,
            "123213 file.txt" to Command.CommandType.UNDEFINED
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(
                "Given input $input" +
                        " when parsing to Command" +
                        " should result with ${expected.name}"
            ) {
                val result = Command.CommandType.fromLine(input)
                assertEquals(expected, result)
            }
        }

    @Test
    fun `it should correctly create file system hierarchy from input`() {
        //given
        val input = listOf(
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d"
        )

        //when
        val result = CommandParser(input).parse()

        //then
        assertNotNull(result)
        assertNull(result.parent)
        assertEquals("root", result.name)
        assertEquals(23352670, result.size())
    }

    @Test
    fun `it should correctly create file system hierarchy with nested directories from input`() {
        //given
        val input = listOf(
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "$ cd a",
            "$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e",
            "$ ls",
            "584 i",
            "$ cd ..",
            "$ cd ..",
            "$ cd d",
            "$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k"
        )

        //when
        val result = CommandParser(input).parse()

        //then
        assertNotNull(result)
        assertNull(result.parent)
        assertEquals("root", result.name)
        assertEquals(48381165, result.size())
    }
}

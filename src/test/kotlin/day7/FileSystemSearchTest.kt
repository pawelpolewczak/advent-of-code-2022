package day7

import day7.model.Directory
import org.junit.jupiter.api.Test
import utils.Utils
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class FileSystemSearchTest {

    @Test
    fun `it should fetch all directories with its size from root directory`() {
        //given
        val input = getCommandsForNestedFileStructure()

        val rootDirectory = CommandParser(input).parse()

        //when
        val result = FileSystemSearch(rootDirectory as Directory).getAllDirectoriesWithSize()

        //then
        assertEquals(6, result.size)
    }

    @Test
    fun `it should fetch all directories with its size from root directory limited by given size`() {
        //given
        val input = getListOfCommands()

        val rootDirectory = CommandParser(input).parse()

        //when
        val result = FileSystemSearch(rootDirectory as Directory).getAllDirectoriesWithSizeLimitedBy(90000)

        //then
        assertEquals(1, result.size)
    }

    @Test
    fun `it should calculate sum of all folders limited by given input`() {
        //given
        val input = getListOfCommands()

        val rootDirectory = CommandParser(input).parse()

        //when
        val result = FileSystemSearch(rootDirectory as Directory).getTotalSizeOfDirectoriesLimitedBy(24933640)

        //then
        assertEquals(95437, result)
    }

    @Test
    fun `it should calculate sum of all folders limited by given input from text file`() {
        //given
        val input = getInputFromFile()

        val rootDirectory = CommandParser(input).parse()

        //when
        val result = FileSystemSearch(rootDirectory as Directory).getTotalSizeOfDirectoriesLimitedBy(100000)

        //then
        assertEquals(1423358, result)
    }

    @Test
    fun `it find the smallest directory above given limit`() {
        //given
        val input = getInputFromFile()

        val rootDirectory = CommandParser(input).parse()
        val fileSystemSearch = FileSystemSearch(rootDirectory as Directory)
        val limit = fileSystemSearch.getSpaceRequiredForUpdateInstall(70000000, 30000000)

        //when
        val result = FileSystemSearch(rootDirectory).getSmallestDirectoryAboveLimit(limit)

        //then
        assertNotNull(result)
        assertEquals(545729, result.size())
    }

    private fun getInputFromFile() = Utils.readFileAsListOfLines("/day7/input.txt")

    private fun getListOfCommands() = listOf(
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

    private fun getCommandsForNestedFileStructure() =
        listOf(
            "$ cd /",
            "$ ls",
            "dir a",
            "dir b",
            "dir c",
            "$ cd a",
            "$ ls",
            "dir e",
            "dir f",
            "123 file.txt",
            "$ cd e",
            "$ ls",
            "dir z",
            "123 x.temp"
            )
}

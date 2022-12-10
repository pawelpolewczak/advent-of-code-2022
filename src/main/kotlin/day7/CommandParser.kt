package day7

import day7.model.Directory
import day7.model.File
import day7.model.FileSystemElement

class CommandParser(private val inputLines: List<String>) {

    fun parse(): FileSystemElement {
        return inputLines.map { Pair(it, Command.CommandType.fromLine(it)) }
            .fold(mutableListOf<Pair<Command, MutableList<String>>>()) { acc, pair ->
                acc.apply {
                    when (pair.second) {
                        Command.CommandType.UNDEFINED -> last().second.add(pair.first)
                        else -> add(createInitialPairOfCommandWithResult(pair))
                    }
                }
            }
            .apply { add(Pair(Command(Command.CommandType.CHANGE_DIRECTORY, "/"), mutableListOf())) }
            .fold(Directory("root", null, mutableListOf())) { acc, pair ->
                when (pair.first.type) {
                    Command.CommandType.CHANGE_DIRECTORY -> when (pair.first.argument) {
                        ".." -> acc.parent as Directory
                        "/" -> getRootFrom(acc)
                        else -> acc.getFolderByName(pair.first.argument)
                    }
                    Command.CommandType.LIST_ITEMS -> acc.apply {
                        addElements(pair.second.map {
                            toFileSystemElement(
                                it,
                                acc
                            )
                        })
                    }
                    else -> acc
                }
            }
    }

    private fun toFileSystemElement(elementAsString: String, parent: FileSystemElement): FileSystemElement {
        val splitLine = elementAsString.split(' ')
        return when (splitLine.component1()) {
            "dir" -> Directory(splitLine.component2(), parent, mutableListOf())
            else -> File(splitLine.component2(), splitLine.component1().toInt(), parent)
        }

    }

    private fun getRootFrom(directory: Directory): Directory {
        var current = directory
        while (current.parent != null) {
            current = current.parent as Directory
        }
        return current
    }

    private fun createInitialPairOfCommandWithResult(pair: Pair<String, Command.CommandType>): Pair<Command, MutableList<String>> =
        Pair(
            Command(
                pair.second,
                pair.first.split(' ').getOrNull(2).toString()
            ),
            mutableListOf()
        )


}


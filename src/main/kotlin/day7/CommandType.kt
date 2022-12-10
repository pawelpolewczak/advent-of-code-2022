package day7

import java.util.*

class Command(val type: CommandType, val argument: String) {

    enum class CommandType(val shortName: String) {
        CHANGE_DIRECTORY("cd"),
        LIST_ITEMS("ls"),
        UNDEFINED("");

        companion object {
            fun fromLine(input: String): CommandType {
                if (!input.startsWith('$')) {
                    return UNDEFINED
                }

                val shortName = input.split(' ').component2().toString()
                return byShortName(shortName)
            }

            private fun byShortName(shortName: String): CommandType {
                return Optional.ofNullable(values().find { it.shortName == shortName })
                    .orElse(UNDEFINED)
            }
        }
    }
}

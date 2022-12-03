package utils

class Utils {

    companion object {
        fun readFileAsListOfLines(path: String): List<String> {
            return object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()!!
        }
    }
}

package day7

import day7.model.Directory

class FileSystemSearch(val rootElement: Directory) {

    fun getAllDirectoriesWithSize(): List<Pair<Directory, Int>> {
        return rootElement.getAllNestedFolders()
            .map { Pair(it, it.size()) }
    }

    fun getAllDirectoriesWithSizeLimitedBy(limit: Int): List<Pair<Directory, Int>> {
        return getAllDirectoriesWithSize().filter { it.second <= limit }
    }

    fun getTotalSizeOfDirectoriesLimitedBy(limit: Int): Int {
        return getAllDirectoriesWithSizeLimitedBy(limit).sumOf { it.second }
    }

    fun getSmallestDirectoryAboveLimit(limit: Int): Directory {
        return getAllDirectoriesWithSize().filter { it.second >= limit }.minByOrNull { it.second }!!.first
    }

    fun getSpaceRequiredForUpdateInstall(deviceSpace: Int, updateSpace: Int): Int {
        return updateSpace - (deviceSpace - rootElement.size())
    }
}

package day7.model

class Directory(name: String, parent: FileSystemElement?, private val elements: MutableList<FileSystemElement>) :
    FileSystemElement(name, parent) {

    fun addElements(elementsToAdd: List<FileSystemElement>) {
        if (elements.isEmpty()) {
            elements.addAll(elementsToAdd)
        }
    }

    override fun size(): Int {
        return elements.sumOf { it.size() }
    }

    fun getFolderByName(name: String): Directory {
        return elements.first { it.name.equals(name) } as Directory
    }

    fun getAllFolders(): List<Directory> {
        return elements.filter { it is Directory }.map { javaClass.cast(it) }
    }

    fun getAllNestedFolders(): Set<Directory> {
        val folders = getAllFolders()
        return folders.flatMap { it.getAllNestedFolders() } union folders
    }

}

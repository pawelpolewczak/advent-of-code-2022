package day7.model

class File(name: String, private val size: Int, parent: FileSystemElement?) : FileSystemElement(name, parent) {

    override fun size(): Int {
        return size
    }
}

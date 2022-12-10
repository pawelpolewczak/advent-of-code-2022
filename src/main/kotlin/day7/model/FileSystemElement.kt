package day7.model

abstract class FileSystemElement(val name: String, val parent: FileSystemElement?) {

    abstract fun size(): Int
}

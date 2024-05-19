package interfaces

import java.io.File

interface IFiles {
    fun fileExists(file: File): Boolean
    fun fileRead(file: File)
}
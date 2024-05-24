package interfaces

import java.io.File

interface IFiles {
    fun fileExists(filepath: String): Boolean
    fun fileRead(filepath: String)

}
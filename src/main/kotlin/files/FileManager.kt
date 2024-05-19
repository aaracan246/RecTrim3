package files

import interfaces.IFiles
import java.io.File

class FileManager: IFiles {

    override fun fileExists(file: File): Boolean { return file.exists() }

    override fun fileRead(file: File) {
        TODO("Not yet implemented")
    }
}
package files

import inputOutput.Console
import inputOutput.InputReceiver
import interfaces.IFiles
import java.io.File

class FileManager(private val console: Console, private val inputReceiver: InputReceiver): IFiles {

    override fun fileExists(filepath: String): Boolean {
        val file = File(filepath)
        return file.exists()
    }

    override fun fileRead(filepath: String) {
        if (!fileExists(filepath)){
            console.writer("File does not exist or something is wrong with the path. $filepath.")
        }

        try {
            val file = File(filepath)
            file.forEachLine {
                val trimmedLine = it.trim()
                if (trimmedLine.isNotEmpty() && !trimmedLine.startsWith("#")) {
                    val args = trimmedLine.split(" ", ";").toTypedArray()
                    inputReceiver.inputMenu(args)
                }
            }
        }
        catch (e: Exception){
            console.writer("Error reading file: ${e.message}.")
        }
    }
}
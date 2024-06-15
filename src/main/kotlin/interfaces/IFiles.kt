package interfaces

import inputOutput.InputReceiver
import java.io.File

interface IFiles {
    fun fileExists(filepath: String): Boolean
    fun fileRead(filepath: String)
    fun tryCommand(command: String, line: List<String>, inputReceiver: InputReceiver)

}
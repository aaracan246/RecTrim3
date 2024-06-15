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
            console.writer("File does not exist or something is wrong with the path. $filepath.", true)
        }

        try {
            val file = File(filepath)

            val allowedCommands = setOf("-g", "-p", "-t", "-e", "-l", "c")
            var currentCommand: String = ""

            file.forEachLine { line ->
                val trimmedLine = line.trim()
                var validArgument: List<String> = mutableListOf()
                val splitLine = trimmedLine.split(";")

                if (trimmedLine.isNotEmpty() && !trimmedLine.startsWith("#")){
                    if (allowedCommands.contains(trimmedLine)){
                        currentCommand = trimmedLine
                    }
                    else if(splitLine.isNotEmpty()){
                        validArgument = splitLine.toList()
                    }
                }
                if (currentCommand.isNotEmpty() && validArgument.isNotEmpty()){
                    tryCommand(currentCommand, validArgument, inputReceiver)
                    console.writer("", true)
                }
                console.writer("", true)
            }
        }
        catch (e: Exception){
            console.writer("Error reading file: ${e.message}.", true)
        }
    }

    override fun tryCommand(command: String, line: List<String>, inputReceiver: InputReceiver) {
        if (line.isNotEmpty()){
            val args = listOf(command) + line
            inputReceiver.inputMenu(args.toTypedArray())
        }
    }
}
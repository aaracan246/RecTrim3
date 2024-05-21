package inputOutput

import entity.GRUPOS
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import javax.sql.DataSource

class InputReceiver(private val console: Console, private val gruposImpl: GRUPOSImpl, private val ctfsImpl: CTFSImpl) {

    fun inputMenu(args: Array<String>){

        when(args[0]){
            "-g" -> {
                val arguments = checkArgs(args, 2, "Not enough arguments. Try: -g <grupoDesc>")
                val grupoDesc = args[1]

                if (arguments != null) { addGroup(grupoDesc) } else{ console.writer("There was an error while trying to add the group.") }
            }


            "-p" -> ""//addParticipation(1, 3)

            "-t" -> {
                val arguments = checkArgs(args, 2, "Not enough arguments. Try: -t <grupoId>")
                val grupoId = args[1].toIntOrNull()

                if (arguments != null) {
                    if (grupoId != null) { deleteGroup(grupoId) } else { console.writer("GroupID must be an integer number and cannot be empty.") }
                } else{ console.writer("Something unexpected happened with the arguments provided.") }
            }



            "-e" -> ""

            "-l" -> {
                val arguments = checkArgs(args, 2, "Not enough arguments. Try: -l <grupoId>")
                val grupoId = args[1].toIntOrNull()

                if (arguments != null){
                    if (grupoId == null){ showAllGroupsInfo() } else { showGroupInfo(grupoId) }
                } else{ console.writer("Something unexpected happened with the arguments provided.") }
            }



            "-c" -> ""

            "-f" -> ""

            "-i" -> ""

            else -> console.writer("That is not a valid command.")
        }
    }

    private fun checkArgs(args: Array<String>, argSize: Int, msg: String): Array<String>? {
        return if (args.size == argSize){
            args.copyOfRange(1, args.size)
        }
        else{
            console.writer(msg)
            null
        }
    }

    private fun addGroup(grupoDesc: String) {
        val grupo = gruposImpl.insertGroup(grupoDesc)

        if (grupo != null){
            console.writer("Group added successfully: ${grupo.grupoId}, ${grupo.grupoDesc}.")
        }
        else{
            console.writer("Something unexpected happened while trying to insert the group.")
        }

    }

    private fun addParticipation(ctfId: Int, puntuacion: Int){  // -p

    }

    private fun deleteGroup(grupoId: Int){
        val successfulDel = gruposImpl.deleteGroup(grupoId)

        if (successfulDel){
            console.writer("Group deleted successfully: $grupoId.")
        }
        else{
            console.writer("Something unexpected happened while trying to delete the group.")
        }
    }

    private fun showGroupInfo(grupoId: Int){
        val grupo = gruposImpl.getGroupById(grupoId)

        if (grupo != null) {
            console.writer("Group: ${grupo.grupoId}, description: ${grupo.grupoDesc}, best position: ${grupo.mejorPosCTFId}.")
        } else {
            console.writer("No data found.")
        }
    }

    private fun showAllGroupsInfo(){ // -l
        val grupo = gruposImpl.getAllGroups()

        if (!grupo.isNullOrEmpty()){
            grupo.forEach{
                console.writer("Group: ${it.grupoDesc}, best position: ${it.mejorPosCTFId}.", true) // Dar formato de salida correcto
            }
        }
        else{ console.writer("No data found.")}
    }
}
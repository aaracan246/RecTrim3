package inputOutput

import entity.CTFS
import entity.GRUPOS
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import java.sql.SQLException
import javax.sql.DataSource

class InputReceiver(private val console: Console, private val gruposImpl: GRUPOSImpl, private val ctfsImpl: CTFSImpl) {

    fun inputMenu(args: Array<String>){

        when(args[0]){
            "-g" -> {
                val arguments = checkArgs(args, 2, "Not enough arguments. Try: -g <grupoDesc>")
                val grupoDesc = args[1]

                if (arguments != null) { addGroup(grupoDesc) } else{ console.writer("There was an error while trying to add the group.") }
            }


            "-p" -> {
                val arguments = checkArgs(args, 4, "Not enough arguments. Try: -p <grupoId> <ctfId> <puntuacion>")
                if (arguments != null) {
                    val grupoId = args[1].toIntOrNull()
                    val ctfId = args[2].toIntOrNull()
                    val puntuacion = args[3].toIntOrNull()

                    if (grupoId != null && ctfId != null && puntuacion != null) {
                        addParticipation(grupoId, ctfId, puntuacion)
                    } else {
                        console.writer("All arguments must be integer numbers and cannot be empty.")
                    }
                } else {
                    console.writer("Something unexpected happened with the arguments provided.")
                }
            }

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

    private fun addGroup(grupoDesc: String) {                   // -g
        val grupo = gruposImpl.insertGroup(grupoDesc)

        if (grupo != null){
            console.writer("Group added successfully: ${grupo.grupoId}, ${grupo.grupoDesc}.")
        }
        else{
            console.writer("Something unexpected happened while trying to insert the group.")
        }
    }

    private fun addParticipation(ctfId: Int,  grupoId: Int, puntuacion: Int){       // -p
        val participationExists = ctfsImpl.getCTFParticipation(ctfId, grupoId)

        if (participationExists != null) {
            participationExists.puntuacion = puntuacion
            ctfsImpl.updateCTFS(participationExists)
        }
        else{
            val newParticipation = CTFS(ctfId, grupoId, puntuacion)
            ctfsImpl.insertCTF(newParticipation)
            console.writer("New CTFS added successfully!")
        }

        calcBestPos(grupoId)
        console.writer("Participation added / updated successfully for group: $grupoId || $ctfId || $puntuacion.")
    }

    //__________________________________________________________________________________________________//
    // Zona de transacciones (WIP):

    private fun calcBestPos(grupoId: Int){
        val participations = ctfsImpl.getAllCTFSById(grupoId)
        val bestParticipation = participations?.maxByOrNull{ it.puntuacion!! }

        if (bestParticipation != null){ gruposImpl.updateBestPosCTF(grupoId, bestParticipation.CTFid)  }
        else { console.writer("No participations data found for group: $grupoId.") }
    }
    //__________________________________________________________________________________________________//

    private fun deleteGroup(grupoId: Int){

        try {
            val allCTFParticipations = ctfsImpl.getAllCTFSById(grupoId)
            allCTFParticipations?.forEach{
                ctfsImpl.deleteCTF(it.CTFid)
            }

            val successfulDel = gruposImpl.deleteGroup(grupoId)

            if (successfulDel){
                console.writer("Group deleted successfully: $grupoId.")
            }
            else{
                console.writer("Something unexpected happened while trying to delete the group.")
            }

        }catch (e: SQLException){
            console.writer("Something unexpected happened while trying to delete the group.")
        }
    }

    private fun showGroupInfo(grupoId: Int){                    // -l
        val grupo = gruposImpl.getGroupById(grupoId)

        if (grupo != null) {
            console.writer("Group: ${grupo.grupoDesc}, best position: ${grupo.mejorPosCTFId}.")
        } else {
            console.writer("No data found.")
        }
    }

    private fun showAllGroupsInfo(){                            // -l
        val grupo = gruposImpl.getAllGroups()

        if (!grupo.isNullOrEmpty()){
            grupo.forEach{
                console.writer("Group: ${it.grupoDesc}, best position: ${it.mejorPosCTFId}.", true) // Dar formato de salida correcto
            }
        }
        else{ console.writer("No data found.")}
    }
}
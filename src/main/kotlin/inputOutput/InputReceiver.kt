package inputOutput

import entity.GRUPOS
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import javax.sql.DataSource

class InputReceiver(private val console: Console, private val gruposImpl: GRUPOSImpl, private val ctfsImpl: CTFSImpl) {

    fun inputMenu(args: Array<String>){

        when(args[0]){
            "-g" -> {
                if (args.size != 2){
                    console.writer("Not enough arguments. Try: -g <grupoDesc>")
                }
                else{
                    val grupoDesc = args[1]
                    addGroup(grupoDesc)
                    console.writer("Se ha añadido con éxito.")
                }
            }


            "-p" -> ""

            "-t" -> {
                if (args.size != 2){
                    console.writer("Not enough arguments. Try: -t <grupoId>")
                }
                else{
                    val grupoId = args[1].toIntOrNull()


                    if (grupoId != null){
                        deleteGroup(grupoId)
                    }
                    else{
                        console.writer("GroupID must be an integer number and cannot be empty.")
                    }
                }
            }


            "-e" -> ""

            "-l" -> {
                if (args.size != 2){
                    console.writer("Not enough arguments. Try: -l <grupoId>")
                }
                else{
                    val grupoId = args[1].toIntOrNull()

                    if (grupoId == null){
                        console.writer("Group data not found, showing all groups:")
                        showAllGroupsInfo()
                    }
                    else{
                        showGroupInfo(grupoId)
                    }
                }
            }

            "-c" -> ""

            "-f" -> ""

            "-i" -> ""

            else -> console.writer("That is not a valid command.")
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

        if (grupo != null && grupo.isNotEmpty()){
            grupo.forEach{
                console.writer("Group: ${it.grupoId}, description: ${it.grupoDesc}, best position: ${it.mejorPosCTFId}.") // Dar formato de salida correcto
            }
        }
        else{ console.writer("No data found.")}
    }
}
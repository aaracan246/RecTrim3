package inputOutput

import servicesImplementation.GRUPOSImpl
import javax.sql.DataSource

class InputReceiver(private val console: Console, private val gruposImpl: GRUPOSImpl) {

    fun inputMenu(args: Array<String>){

        when(args[0]){
            "-g" -> {
                if (args.size < 3){
                    console.writer("Not enough arguments. Try: -g <grupoId> <grupoDesc>")
                }
                else{
                    val grupoId = args[1].toIntOrNull()
                    val grupoDesc = args[2]

                    if (grupoId != null){
                        addGroup(grupoId, grupoDesc)
                    }
                    else{
                        console.writer("GroupID must be an integer number and cannot be empty.")
                    }
                }
            }


            "-p" -> ""

            "-t" -> {
                if (args.size < 2){
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

            "-l" -> ""

            "-c" -> ""

            "-f" -> ""

            "-i" -> ""

            else -> console.writer("That is not a valid command.")
        }
    }

    private fun addGroup(grupoId: Int, grupoDesc: String) {
        val grupo = gruposImpl.insertGroup(grupoId, grupoDesc)

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

    private fun showAllGroupsInfo(){ // -l

    }
}
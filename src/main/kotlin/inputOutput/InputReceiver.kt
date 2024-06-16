package inputOutput

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import ds.DataSourceFactory
import ds.connection.ConnectionManager
import entity.CTFS
import files.FileManager
import gui.GUI
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import java.sql.Connection
import java.sql.SQLException

/**
 * Esta clase recibirá los distintos argumentos y comprobará tanto su extensión como su funcionalidad
 * */
class InputReceiver(private val console: Console, private val gruposImpl: GRUPOSImpl, private val ctfsImpl: CTFSImpl, private val connectionManager: ConnectionManager) {

    private val connection = connectionManager.getConnection()
    /**
     * Esta función recibirá y gestionará los argumentos
     * */

    fun inputMenu(args: Array<String>){

        when(args[0]){
            "-g" -> optionAddGroup(args)

            "-p" -> optionAddParticipation(args)

            "-t" -> optionDeleteGroup(args)

            "-e" -> optionDeleteParticipation(args)

            "-l" -> optionListGroups(args)

            "-c" -> optionListCTF(args)

            "-f" -> optionFiles(args)

            else -> console.writer("That is not a valid command.")
        }
    }

    /**
     * Esta función comprobará la extensión de los argumentos procurados y devolverá una copia de su extensión con el fin de controlar la inserción de args
     * */
    private fun checkArgs(args: Array<String>, argSize: Int, msg: String): Array<String>? {
        return if (args.size == argSize){
            args.copyOfRange(0, args.size)
        }
        else{
            console.writer("There was an unexpected error while checking the arguments provided.", true)
            return null
        }
    }
    //__________________________________________________________________________________________________//
    //Zona de implementación de selector:


    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el añadir grupo
     * */
    private fun optionAddGroup(args: Array<String>){
        val arguments = checkArgs(args, 2, "Not enough arguments. Try: -g <grupoDesc>")
        val grupoDesc = args[1]

        if (arguments != null) { addGroup(grupoDesc) } else{ console.writer("There was an error while trying to add the group.") }
    }


    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el añadir una participación
     * */
    private fun optionAddParticipation(args: Array<String>){
        val arguments = checkArgs(args, 4, "Not enough arguments. Try: -p <grupoId> <ctfId> <puntuacion>")
        if (arguments != null) {
            val grupoId = args[1].toIntOrNull()
            val ctfId = args[2].toIntOrNull()
            val puntuacion = args[3].toIntOrNull()

            if (grupoId != null && ctfId != null && puntuacion != null) {
                try {
                    if (connection != null) {
                        connectionManager.beginTransaction(connection)
                        val grupoExist = connection.let { gruposImpl.getGroupById(grupoId, it) }

                        if (grupoExist != null){
                            addParticipation(grupoId, ctfId, puntuacion)
                            connectionManager.commitTransaction(connection)
                        }
                        else{
                            console.writer("There is no group with that ID associated. ID: $grupoId.", true)
                            connectionManager.rollbackTransaction(connection)
                        }
                    }
                    else{
                        console.writer("Error accessing the database. Something unexpected happened.", true)
                    }
                } catch (e: Exception) {
                     console.writer("Error accessing the database: ${e.message}", true)
                }
                finally {
                    if (connection != null) {
                        connectionManager.closeConnection(connection)
                    }
                }
            } else {
                console.writer("All arguments must be integer numbers and cannot be empty.", true)
            }
        } else {
            console.writer("Something unexpected happened with the arguments provided.", true)
        }
    }

    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el eliminar grupo
     * */
    private fun optionDeleteGroup(args: Array<String>){
        val arguments = checkArgs(args, 2, "Not enough arguments. Try: -t <grupoId>")
        val grupoId = args[1].toIntOrNull()

        if (arguments != null) {
            if (grupoId != null) {
                if (connection != null) {
                    deleteGroup(grupoId, connection)
                }
                else{
                    console.writer("There was an error while trying to establish a connection.", true)
                }
            } else { console.writer("GroupID must be an integer number and cannot be empty.", true) }
        } else{ console.writer("Something unexpected happened with the arguments provided.", true) }
    }

    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el eliminar una participación
     * */
    private fun optionDeleteParticipation(args: Array<String>){
        val arguments = checkArgs(args, 3, "Not enough arguments. Try: -e <ctfId> <grupoId>")
        val ctfId = args[1].toIntOrNull()
        val grupoId = args[2].toIntOrNull()

        if (arguments != null){
            if (ctfId != null && grupoId != null){
                deleteParticipation(ctfId, grupoId)
            }
            else{
                console.writer("Both IDs must be integer numbers and not be null.", true)
            }
        }
        else{
            console.writer("Something unexpected happened while trying to delete the participation.", true)
        }
    }

    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el mostrar grupo
     * */
    private fun optionListGroups(args: Array<String>){
        if (args.size == 1) {
            showAllGroupsInfo()
        }
        else if (args.size == 2) {
            val grupoId = args[1].toIntOrNull()
            if (grupoId != null) {
                showGroupInfo(grupoId)
            } else {
                console.writer("GroupID must be an integer number.", true)
            }
        }
        else {
            console.writer("Not enough arguments. Try: -l <grupoId>", true)
        }
    }


    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el mostrar los CTF
     * */
    private fun optionListCTF(args: Array<String>){
        if (args.size == 1) {
            console.writer("Not enough arguments. Try: -c <ctfId>", true)
        }
        else if (args.size == 2) {
            val ctfId = args[1].toIntOrNull()
            if (ctfId != null) {
                showCTFParticipation(ctfId)
            }
            else {
                console.writer("CTFID must be an integer number and cannot be empty.", true)
            }
        }
        else {
            console.writer("Too many arguments. Try: -c <ctfId>", true)
        }
    }


    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el leer ficheros
     * */

    private fun optionFiles(args: Array<String>){
        if (args.size < 2){
            console.writer("Not enough arguments. Try -f <filepath>", true)
            return
        }

        val filepath = args[1]
        FileManager(console, this).fileRead(filepath)
    }

    //__________________________________________________________________________________________________//


    /**
     * Esta función implementa la funcionalidad de añadir un grupo
     * */
    private fun addGroup(grupoDesc: String) {                   // -g
        val grupo = connection?.let { gruposImpl.insertGroup(grupoDesc, it) }

        if (grupo != null){
            console.writer("Group added successfully: ${grupo.grupoId}, ${grupo.grupoDesc}.")
        }
        else{
            console.writer("Something unexpected happened while trying to insert the group.")
        }
    }

    /**
     * Esta función implementa la funcionalidad de añadir una participación
     * */
    private fun addParticipation(ctfId: Int,  grupoId: Int, puntuacion: Int){       // -p
        val participationExists = connection?.let { ctfsImpl.getCTFParticipation(ctfId, grupoId, it) }

        if (participationExists != null) {
            participationExists.puntuacion = puntuacion
            if (connection != null) {
                ctfsImpl.updateCTFS(participationExists, connection)
            }
        }
        else{
            val newParticipation = CTFS(ctfId, grupoId, puntuacion)
            if (connection != null) {
                ctfsImpl.insertCTF(newParticipation, connection)
            }
            console.writer("New CTFS added successfully!")
        }

        calcBestPos(grupoId)
        console.writer("Participation added / updated successfully: CTFID: $ctfId || GROUPID: $grupoId || SCORE: $puntuacion.", true)
    }

    //__________________________________________________________________________________________________//
    // Zona de transacciones (WIP):

    /**
     * Esta función calcula la mejor posición de todos los ctf del grupo deseado y los updateo
     * */
    private fun calcBestPos(grupoId: Int){
        val participations = connection?.let { ctfsImpl.getAllCTFSById(grupoId, it) }
        val bestParticipation = participations?.maxByOrNull{ it.puntuacion?: 0 }

        if (bestParticipation != null){
            if (connection != null) {
                gruposImpl.updateBestPosCTF(grupoId, bestParticipation.CTFid, connection)
            }
        }
        else { console.writer("No participations data found for group: $grupoId.") }
    }

    /**
     * Esta función implementa la funcionalidad de eliminar un grupo
     * */
    private fun deleteGroup(grupoId: Int, connection: Connection){

        try {

            connectionManager.beginTransaction(connection)

            gruposImpl.updateBestPosCTF(grupoId, null, connection)

            val allCTFParticipations = ctfsImpl.getAllCTFSById(grupoId, connection)
            allCTFParticipations?.forEach {
                ctfsImpl.deleteCTF(it.CTFid, it.grupoid, connection )
            }

            val successfulDel = gruposImpl.deleteGroup(grupoId, connection)

            if (successfulDel){
                connectionManager.commitTransaction(connection)
                console.writer("Group deleted successfully: $grupoId.")
            }
            else{
                connectionManager.rollbackTransaction(connection)
                console.writer("Something unexpected happened while trying to delete the group. $grupoId.")
            }

        }catch (e: SQLException){
            connectionManager.rollbackTransaction(connection)
            console.writer("Something unexpected happened while trying to delete the group. $grupoId.")
        }
        finally {
            connectionManager.closeConnection(connection)
        }
    }

    /**
     * Esta función implementa la funcionalidad de eliminar una participación
     * */
    private fun deleteParticipation(ctfId: Int, grupoId: Int){
        try {

            if (connection != null) {
                gruposImpl.updateBestPosCTF(grupoId, null, connection)
            }

            val participationExists = connection?.let { ctfsImpl.getCTFParticipation(ctfId, grupoId, it) }
            if (participationExists != null){
                if (connection != null) {
                    ctfsImpl.deleteCTF(ctfId, grupoId, connection)
                }
                console.writer("Participation deleted successfully: CTFID: $ctfId || GROUPID: $grupoId.")
                calcBestPos(grupoId)
            }
            else{
                console.writer("No participation found for: CTFID: $ctfId || GROUPID: $grupoId.")
            }
        }
        catch (e: Exception){
            console.writer("Something unexpected happened while trying to delete the participation.")
        }
    }
    //__________________________________________________________________________________________________//


    /**
     * Esta función implementa la funcionalidad de mostrar la información según ID del grupo
     * */
    private fun showGroupInfo(grupoId: Int){                    // -l
        val grupo = connection?.let { gruposImpl.getGroupById(grupoId, it) }

        if (grupo != null) {
            console.writer("Group: ${grupo.grupoDesc}, best position: ${grupo.mejorPosCTFId}.")
        } else {
            console.writer("No data found.")
        }
    }


    /**
     * Esta función implementa la funcionalidad de mostrar todos los grupos
     * */
    private fun showAllGroupsInfo(){                            // -l
        val grupo = connection?.let { gruposImpl.getAllGroups(it) }

        if (!grupo.isNullOrEmpty()){
            grupo.forEach{
                console.writer("Group: ${it.grupoDesc}, best position: ${it.mejorPosCTFId}.", true) // Dar formato de salida correcto
            }
        }
        else{ console.writer("No data found.")}
    }


    /**
     * Esta función implementa la funcionalidad de mostrar todas las participaciones de un grupo por ID
     * */
    private fun showCTFParticipation(ctfId: Int) {              // -c
        val participations = connection?.let { ctfsImpl.getAllCTFSById(ctfId, it) }

        if (!participations.isNullOrEmpty()) {
            participations.forEach {
                console.writer("Group ID: ${it.grupoid}, CTF ID: ${it.CTFid}, puntuacion: ${it.puntuacion}.", true)
            }
        }
        else {
            console.writer("No participations found for CTFID $ctfId.")
        }
    }
}
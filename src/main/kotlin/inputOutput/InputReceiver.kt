package inputOutput

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import ds.DataSourceFactory
import entity.CTFS
import files.FileManager
import gui.GUI
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import java.sql.SQLException

/**
 * Esta clase recibirá los distintos argumentos y comprobará tanto su extensión como su funcionalidad
 * */
class InputReceiver(private val console: Console, private val gruposImpl: GRUPOSImpl, private val ctfsImpl: CTFSImpl) {

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
            args.copyOfRange(1, args.size)
        }
        else{
            console.writer(msg)
            null
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
                addParticipation(grupoId, ctfId, puntuacion)
            } else {
                console.writer("All arguments must be integer numbers and cannot be empty.")
            }
        } else {
            console.writer("Something unexpected happened with the arguments provided.")
        }
    }

    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el eliminar grupo
     * */
    private fun optionDeleteGroup(args: Array<String>){
        val arguments = checkArgs(args, 2, "Not enough arguments. Try: -t <grupoId>")
        val grupoId = args[1].toIntOrNull()

        if (arguments != null) {
            if (grupoId != null) { deleteGroup(grupoId) } else { console.writer("GroupID must be an integer number and cannot be empty.") }
        } else{ console.writer("Something unexpected happened with the arguments provided.") }
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
                console.writer("Both IDs must be integer numbers and not be null.")
            }
        }
        else{
            console.writer("Something unexpected happened while trying to delete the participation.")
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
                console.writer("GroupID must be an integer number.")
            }
        }
        else {
            console.writer("Not enough arguments. Try: -l <grupoId>")
        }
    }


    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el mostrar los CTF
     * */
    private fun optionListCTF(args: Array<String>){
        if (args.size == 1) {
            console.writer("Not enough arguments. Try: -c <ctfId>")
        }
        else if (args.size == 2) {
            val ctfId = args[1].toIntOrNull()
            if (ctfId != null) {
                showCTFParticipation(ctfId)
            }
            else {
                console.writer("CTFID must be an integer number and cannot be empty.")
            }
        }
        else {
            console.writer("Too many arguments. Try: -c <ctfId>")
        }
    }


    /**
     * Esta función sirve como intermedio entre el menú y la función que implementa el leer ficheros
     * */

    private fun optionFiles(args: Array<String>){
        val arguments = checkArgs(args, 2, "Not enough arguments. Try: -f <filepath>")
        val filepath = args[1]
        if (arguments != null) {
            FileManager(console, this).fileRead(filepath)
        } else {
            console.writer("Something unexpected happened with the arguments provided.")
        }
    }

    //__________________________________________________________________________________________________//


    /**
     * Esta función implementa la funcionalidad de añadir un grupo
     * */
    private fun addGroup(grupoDesc: String) {                   // -g
        val grupo = gruposImpl.insertGroup(grupoDesc)

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

    /**
     * Esta función calcula la mejor posición de todos los ctf del grupo deseado y los updateo
     * */
    private fun calcBestPos(grupoId: Int){
        val participations = ctfsImpl.getAllCTFSById(grupoId)
        val bestParticipation = participations?.maxByOrNull{ it.puntuacion?: 0 }

        if (bestParticipation != null){ gruposImpl.updateBestPosCTF(grupoId, bestParticipation.CTFid)  }
        else { console.writer("No participations data found for group: $grupoId.") }
    }

    /**
     * Esta función implementa la funcionalidad de eliminar un grupo
     * */
    private fun deleteGroup(grupoId: Int){
        val connection = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI).connection

        try {

            connection.autoCommit = false

            gruposImpl.updateBestPosCTF(grupoId, null)

            val allCTFParticipations = ctfsImpl.getAllCTFSById(grupoId)
            allCTFParticipations?.forEach {
                ctfsImpl.deleteCTF(it.CTFid, it.grupoid )
            }

            val successfulDel = gruposImpl.deleteGroup(grupoId)

            if (successfulDel){
                connection.commit()
                console.writer("Group deleted successfully: $grupoId.")
            }
            else{
                connection.rollback()
                console.writer("Something unexpected happened while trying to delete the group. $grupoId.")
            }

        }catch (e: SQLException){
            connection.rollback()
            console.writer("Something unexpected happened while trying to delete the group. $grupoId.")
        }
        finally {
            connection.autoCommit = true
            connection.close()
        }
    }

    /**
     * Esta función implementa la funcionalidad de eliminar una participación
     * */
    private fun deleteParticipation(ctfId: Int, grupoId: Int){
        try {

            gruposImpl.updateBestPosCTF(grupoId, null)

            val participationExists = ctfsImpl.getCTFParticipation(ctfId, grupoId)
            if (participationExists != null){
                ctfsImpl.deleteCTF(ctfId, grupoId)
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
        val grupo = gruposImpl.getGroupById(grupoId)

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
        val grupo = gruposImpl.getAllGroups()

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
        val participations = ctfsImpl.getAllCTFSById(ctfId)

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
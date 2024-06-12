package viewmodel

import androidx.compose.runtime.mutableStateListOf
import entity.GRUPOS
import interfaces.services.GRUPOSService
import java.io.File


class ViewModel(private val gruposService: GRUPOSService) {
    private val _grupos = mutableStateListOf<GRUPOS>()
    val grupos: List<GRUPOS> = _grupos


    init {
        retrieveGroupInfo()
    }

    private fun retrieveGroupInfo() {
        val allgroups = gruposService.getAllGroups()

        allgroups?.forEach{
            _grupos.add(it)
        }

    }

    fun filter(groupDesc: String): List<GRUPOS>{
        return _grupos.filter { it.grupoDesc.contains(groupDesc) }
    }

    fun exportar(filepath: String, grupos: List<GRUPOS>){
        val groupedById = grupos.groupBy { it.grupoId }
        val file = File(filepath)

        groupedById.forEach{ (id, groupDesc) ->
            file.appendText("CTF participartion: \n")
            groupDesc.sortedByDescending { it.mejorPosCTFId }.forEachIndexed{ index, grupos ->
                file.appendText("ID: $id. ${grupos.grupoDesc} (${grupos.mejorPosCTFId} puntos)\n")
            }
            file.appendText(" ")
        }
    }
}
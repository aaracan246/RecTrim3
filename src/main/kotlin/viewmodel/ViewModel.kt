package viewmodel

import androidx.compose.runtime.mutableStateListOf
import entity.GRUPOS
import interfaces.services.GRUPOSService


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

    private fun filter(){
        TODO()
    }

    private fun exportar(){
        TODO()
    }
}
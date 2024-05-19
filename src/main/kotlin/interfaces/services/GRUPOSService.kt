package interfaces.services

import entity.GRUPOS

interface GRUPOSService {
    fun insertGroup(id: Int, grupoDesc: String, mejorPosCTFId: Int = 0): GRUPOS?
    fun getAllGroups(): List<GRUPOS>?
    fun getGroupById(id: Int): GRUPOS?
    fun updateGroups(grupos: GRUPOS): GRUPOS?
    fun deleteGroup(id: Int): Boolean
}
package interfaces.services

import entity.GRUPOS

interface GRUPOSService {
    fun insertGroup(grupoDesc: String): GRUPOS?
    fun getAllGroups(): List<GRUPOS>?
    fun getGroupById(id: Int): GRUPOS?
    fun updateGroups(grupos: GRUPOS): GRUPOS?
    fun deleteGroup(id: Int): Boolean
    fun updateBestPosCTF(grupoId: Int, CTFId: Int): Boolean
}
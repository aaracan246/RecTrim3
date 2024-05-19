package interfaces

import entity.GRUPOS

interface IGRUPOSdao {
    fun insertGroup(grupos: GRUPOS): GRUPOS?
    fun getAllGroups(): List<GRUPOS>?
    fun getGroupById(id: Int): GRUPOS?
    fun updateGroups(grupos: GRUPOS): GRUPOS?
    fun deleteGroup(id: Int): Boolean
}
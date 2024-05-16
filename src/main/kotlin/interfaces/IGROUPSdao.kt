package interfaces

import entity.GRUPOS
import java.util.*

interface IGROUPSdao {
    fun createGroup(grupos: GRUPOS): GRUPOS?
    fun getAllGroups(): List<GRUPOS>?
    fun getGroupById(id: UUID): GRUPOS?
    fun updateGroups(group: GRUPOS): GRUPOS?
    fun deleteGroup(id: UUID): Boolean
}
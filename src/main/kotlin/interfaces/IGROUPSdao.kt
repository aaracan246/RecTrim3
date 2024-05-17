package interfaces

import entity.GRUPOS
import java.util.*

interface IGROUPSdao {
    fun createGroup(grupos: GRUPOS): GRUPOS?
    fun getAllGroups(): List<GRUPOS>?
    fun getGroupById(id: Int): GRUPOS?
    fun updateGroups(grupos: GRUPOS): GRUPOS?
    fun deleteGroup(id: Int): Boolean
}
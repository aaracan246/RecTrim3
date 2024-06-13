package interfaces.services

import entity.GRUPOS
import java.sql.Connection

interface GRUPOSService {
    fun insertGroup(grupoDesc: String, connection: Connection): GRUPOS?
    fun getAllGroups(connection: Connection): List<GRUPOS>?
    fun getGroupById(id: Int, connection: Connection): GRUPOS?
    fun updateGroups(grupos: GRUPOS, connection: Connection): GRUPOS?
    fun deleteGroup(id: Int, connection: Connection): Boolean
    fun updateBestPosCTF(grupoId: Int, CTFId: Int?, connection: Connection): Boolean
}
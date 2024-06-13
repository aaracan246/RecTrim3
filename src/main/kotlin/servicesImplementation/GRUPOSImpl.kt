package servicesImplementation

import ds.connection.ConnectionManager
import entity.GRUPOS
import interfaces.IGRUPOSdao
import interfaces.services.GRUPOSService
import java.sql.Connection

class GRUPOSImpl(private val gruposDAO: IGRUPOSdao?): GRUPOSService {

    override fun insertGroup(grupoDesc: String, connection: Connection): GRUPOS? {
        val grupo = GRUPOS(grupoDesc = grupoDesc)
        return gruposDAO?.insertGroup(grupo, connection)
    }

    override fun getAllGroups(connection: Connection): List<GRUPOS>? {
        return gruposDAO?.getAllGroups(connection)
    }

    override fun getGroupById(id: Int, connection: Connection): GRUPOS? {
        return gruposDAO?.getGroupById(id, connection)
    }

    override fun updateGroups(grupos: GRUPOS, connection: Connection): GRUPOS? {
        return gruposDAO?.updateGroups(grupos, connection)
    }

    override fun deleteGroup(id: Int, connection: Connection): Boolean {
        return gruposDAO?.deleteGroup(id, connection)?: false
    }

    override fun updateBestPosCTF(grupoId: Int, CTFId: Int?, connection: Connection): Boolean {
        return gruposDAO?.updateBestPosCTF(grupoId, CTFId, connection) ?: false
    }


}
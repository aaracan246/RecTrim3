package dao.xml

import entity.GRUPOS
import interfaces.IGRUPOSdao
import java.sql.Connection

class XMLGRUPODAO: IGRUPOSdao {
    override fun insertGroup(grupos: GRUPOS, connection: Connection): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun getAllGroups(connection: Connection): List<GRUPOS>? {
        TODO("Not yet implemented")
    }

    override fun getGroupById(id: Int, connection: Connection): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun updateGroups(grupos: GRUPOS, connection: Connection): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(id: Int, connection: Connection): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateBestPosCTF(grupoId: Int, CTFId: Int?, connection: Connection): Boolean {
        TODO("Not yet implemented")
    }
}
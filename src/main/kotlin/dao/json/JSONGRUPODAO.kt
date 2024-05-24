package dao.json

import entity.GRUPOS
import interfaces.IGRUPOSdao

class JSONGRUPODAO: IGRUPOSdao {
    override fun insertGroup(grupos: GRUPOS): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun getAllGroups(): List<GRUPOS>? {
        TODO("Not yet implemented")
    }

    override fun getGroupById(id: Int): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun updateGroups(grupos: GRUPOS): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateBestPosCTF(grupoId: Int, CTFId: Int?): Boolean {
        TODO("Not yet implemented")
    }
}
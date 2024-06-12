package servicesImplementation

import entity.GRUPOS
import interfaces.IGRUPOSdao
import interfaces.services.GRUPOSService

class GRUPOSImpl(private val gruposDAO: IGRUPOSdao?): GRUPOSService {

    override fun insertGroup(grupoDesc: String): GRUPOS? {
        val grupo = GRUPOS(grupoDesc = grupoDesc)
        return gruposDAO?.insertGroup(grupo)
    }

    override fun getAllGroups(): List<GRUPOS>? {
        return gruposDAO?.getAllGroups()
    }

    override fun getGroupById(id: Int): GRUPOS? {
        return gruposDAO?.getGroupById(id)
    }

    override fun updateGroups(grupos: GRUPOS): GRUPOS? {
        return gruposDAO?.updateGroups(grupos)
    }

    override fun deleteGroup(id: Int): Boolean {
        return gruposDAO?.deleteGroup(id)?: false
    }

    override fun updateBestPosCTF(grupoId: Int, CTFId: Int?): Boolean {
        return gruposDAO?.updateBestPosCTF(grupoId, CTFId) ?: false
    }


}
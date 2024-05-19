package servicesImplementation

import dao.GRUPOSdao
import entity.GRUPOS
import interfaces.services.GRUPOSService

class GRUPOSImpl(private val gruposDAO: GRUPOSdao): GRUPOSService {

    override fun insertGroup(id: Int, grupoDesc: String, mejorPosCTFId: Int): GRUPOS? {
        val grupo = GRUPOS(id, grupoDesc, mejorPosCTFId)
        return gruposDAO.insertGroup(grupo)
    }

    override fun getAllGroups(): List<GRUPOS>? {
        return gruposDAO.getAllGroups()
    }

    override fun getGroupById(id: Int): GRUPOS? {
        return gruposDAO.getGroupById(id)
    }

    override fun updateGroups(grupos: GRUPOS): GRUPOS? {
        return gruposDAO.updateGroups(grupos)
    }

    override fun deleteGroup(id: Int): Boolean {
        return gruposDAO.deleteGroup(id)
    }

}
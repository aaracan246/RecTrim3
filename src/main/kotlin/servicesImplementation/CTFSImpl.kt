package servicesImplementation

import dao.CTFSdao
import entity.CTFS
import interfaces.services.CTFSService

class CTFSImpl(private val ctfsDAO: CTFSdao): CTFSService {
    override fun insertCTF(ctf: CTFS): CTFS? {
        return ctfsDAO.insertCTF(ctf)
    }

    override fun getCTFParticipation(ctfId: Int, grupoId: Int): CTFS? {
        return ctfsDAO.getCTFParticipation(grupoId, ctfId)
    }

    override fun getAllCTFSById(ctfId: Int): List<CTFS>? {
        return ctfsDAO.getAllCTFSById(ctfId)
    }


    override fun updateCTFS(ctf: CTFS): CTFS? {
        return ctfsDAO.updateCTFS(ctf)
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int): Boolean {
        return ctfsDAO.deleteCTF(ctfId, grupoId)
    }

}
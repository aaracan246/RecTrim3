package servicesImplementation

import dao.CTFSdao
import entity.CTFS
import interfaces.services.CTFSService

class CTFSImpl(private val ctfsDAO: CTFSdao): CTFSService {
    override fun insertCTF(ctf: CTFS): CTFS? {
        return ctfsDAO.insertCTF(ctf)
    }

    override fun getAllCTFS(): List<CTFS>? {
        return ctfsDAO.getAllCTFS()
    }

    override fun getCTFById(id: Int): CTFS? {
        return ctfsDAO.getCTFById(id)
    }

    override fun updateCTFS(ctf: CTFS): CTFS? {
        return ctfsDAO.updateCTFS(ctf)
    }

    override fun deleteCTF(id: Int): Boolean {
        return ctfsDAO.deleteCTF(id)
    }

}
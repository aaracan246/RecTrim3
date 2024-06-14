package servicesImplementation

import entity.CTFS
import interfaces.ICTFdao
import interfaces.services.CTFSService
import java.sql.Connection

class CTFSImpl(private val ctfsDAO: ICTFdao?): CTFSService {
    override fun insertCTF(ctf: CTFS, connection: Connection): CTFS? {
        return ctfsDAO?.insertCTF(ctf, connection)
    }

    override fun getCTFParticipation(ctfId: Int, grupoId: Int, connection: Connection): CTFS? {
        return ctfsDAO?.getCTFParticipation(grupoId, ctfId, connection)
    }

    override fun getAllCTFSById(ctfId: Int, connection: Connection): List<CTFS>? {
        return ctfsDAO?.getAllCTFSById(ctfId, connection)
    }


    override fun updateCTFS(ctf: CTFS, connection: Connection): CTFS? {
        return ctfsDAO?.updateCTFS(ctf, connection)
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int, connection: Connection): Boolean {
        return ctfsDAO?.deleteCTF(ctfId, grupoId, connection) ?: false
    }

}
package dao.json

import entity.CTFS
import interfaces.ICTFdao
import java.sql.Connection

class JSONCTFDAO: ICTFdao {
    override fun insertCTF(ctf: CTFS, connection: Connection): CTFS? {
        TODO("Not yet implemented")
    }

    override fun getCTFParticipation(grupoId: Int, ctfId: Int, connection: Connection): CTFS? {
        TODO("Not yet implemented")
    }

    override fun getAllCTFSById(ctfId: Int, connection: Connection): List<CTFS>? {
        TODO("Not yet implemented")
    }

    override fun updateCTFS(ctf: CTFS, connection: Connection): CTFS? {
        TODO("Not yet implemented")
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int, connection: Connection): Boolean {
        TODO("Not yet implemented")
    }
}
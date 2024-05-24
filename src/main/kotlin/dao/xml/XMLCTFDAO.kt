package dao.xml

import entity.CTFS
import interfaces.ICTFdao

class XMLCTFDAO: ICTFdao {
    override fun insertCTF(ctf: CTFS): CTFS? {
        TODO("Not yet implemented")
    }

    override fun getCTFParticipation(grupoId: Int, ctfId: Int): CTFS? {
        TODO("Not yet implemented")
    }

    override fun getAllCTFSById(ctfId: Int): List<CTFS>? {
        TODO("Not yet implemented")
    }

    override fun updateCTFS(ctf: CTFS): CTFS? {
        TODO("Not yet implemented")
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int): Boolean {
        TODO("Not yet implemented")
    }

}
package interfaces

import entity.CTFS

interface ICTFdao {
    fun insertCTF(ctf: CTFS): CTFS?
    fun getCTFParticipation(grupoId: Int, ctfId: Int): CTFS?
    fun getAllCTFSById(ctfId: Int): List<CTFS>?
    fun updateCTFS(ctf: CTFS): CTFS?
    fun deleteCTF(id: Int): Boolean
}
package interfaces

import entity.CTFS
import java.sql.Connection

interface ICTFdao {
    fun insertCTF(ctf: CTFS, connection: Connection): CTFS?
    fun getCTFParticipation(grupoId: Int, ctfId: Int, connection: Connection): CTFS?
    fun getAllCTFSById(ctfId: Int, connection: Connection): List<CTFS>?
    fun updateCTFS(ctf: CTFS, connection: Connection): CTFS?
    fun deleteCTF(ctfId: Int, grupoId: Int, connection: Connection): Boolean
}
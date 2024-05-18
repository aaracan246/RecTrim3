package interfaces

import entity.CTFS

interface ICTFdao {
    fun insertCTF(ctf: CTFS): CTFS?
    fun getAllCTFS(): List<CTFS>?
    fun getCTFById(id: Int): CTFS?
    fun updateCTFS(ctf: CTFS): CTFS?
    fun deleteCTF(id: Int): Boolean
}
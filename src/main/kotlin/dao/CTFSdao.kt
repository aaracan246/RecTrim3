package dao

import entity.CTFS
import interfaces.ICTFdao
import java.sql.SQLException
import javax.sql.DataSource

class CTFSdao(private val dataSource: DataSource): ICTFdao{

    override fun insertCTF(ctf: CTFS): CTFS? {
        val sql = "INSERT INTO CTFS (CTFID, GRUPOID, PUNTUACION) VALUES (?, ?, ?)"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, ctf.CTFid)
                statement.setInt(2, ctf.grupoid)
                ctf.puntuacion?.let { statement.setInt(3, it) }
                val rs = statement.executeUpdate()
                if (rs == 1) {
                    return ctf
                }
                else {
                    throw SQLException("Something unexpected happened while trying to insert the data.")
                }
            }
        }
    }

    override fun getCTFParticipation(grupoId: Int, ctfId: Int): CTFS? {
        val sql = "SELECT * FROM CTFS WHERE GRUPOID = ? AND CTFID = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, grupoId)
                statement.setInt(2, ctfId)
                val rs = statement.executeQuery()
                if (rs.next()){
                    return CTFS(
                        grupoid = rs.getInt("GRUPOID"),
                        CTFid = rs.getInt("CTFID"),
                        puntuacion = rs.getInt("PUNTUACION")
                    )
                }
                else{
                    return null
                }
            }
        }
    }


    override fun getAllCTFSById(grupoId: Int): List<CTFS>? {
        val sql = "SELECT * FROM CTFS WHERE GRUPOID = ? ORDER BY PUNTUACION DESC"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, grupoId)
                val rs = statement.executeQuery()
                val ctfs = mutableListOf<CTFS>()
                while (rs!!.next()){
                    ctfs.add(
                        CTFS(
                            grupoid = rs.getInt("GRUPOID"),   // Quizás recuperar ID - checkear DB
                            CTFid = rs.getInt("CTFID"),
                            puntuacion = rs.getInt("PUNTUACION")
                        )
                    )
                }
                if (ctfs != null){
                    return ctfs
                }
                else{
                    throw SQLException("Something unexpected happened while trying to retrieve groups data.")
                }
            }
        }
    }


    override fun updateCTFS(ctf: CTFS): CTFS? {
        val sql = "UPDATE CTFS SET PUNTUACION = ? WHERE CTFID = ? AND GRUPOID = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                ctf.puntuacion?.let { statement.setInt(1, it) }
                statement.setInt(2, ctf.CTFid)
                statement.setInt(3, ctf.grupoid)
                statement.executeUpdate()
                if (ctf != null){
                    return ctf
                }
                else{
                    throw SQLException("Something unexpected happened while trying to update groups data.")
                }
            }
        }
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int): Boolean {
        val sql = "DELETE FROM CTFS WHERE CTFID = ? AND GRUPOID = ?"
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setInt(1, ctfId)
                    statement.setInt(2, grupoId)
                    statement.executeUpdate()
                    return true
                }
            }
        }
        catch (e: Exception){
            throw SQLException("Something unexpected happened while trying to delete participation data.")
        }
    }
}
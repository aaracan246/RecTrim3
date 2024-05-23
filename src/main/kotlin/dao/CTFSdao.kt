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
                        CTFid = rs.getInt("CTFID"),
                        grupoid = rs.getInt("GRUPOID"),
                        puntuacion = rs.getInt("PUNTUACION")
                    )
                }
                else{
                    return null
                }
            }
        }
    }


    override fun getAllCTFSById(ctfId: Int): List<CTFS>? {
        val sql = "SELECT * FROM CTFS WHERE GRUPOID = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, ctfId)
                val rs = statement.executeQuery()
                val ctfs = mutableListOf<CTFS>()
                while (rs!!.next()){
                    ctfs.add(
                        CTFS(
                            CTFid = rs.getInt("CTFID"),
                            grupoid = rs.getInt("GRUPOID"),   // Quizás recuperar ID - checkear DB
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
        val sql = "UPDATE CTFS SET CTFID = ?, GRUPOID = ?, PUNTUACION = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, ctf.CTFid)
                statement.setInt(2, ctf.grupoid)
                ctf.puntuacion?.let { statement.setInt(3, it) }
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

    override fun deleteCTF(id: Int): Boolean {
        val sql = "DELETE FROM CTFS WHERE CTFID = (?)"
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setString(1, id.toString())
                    statement.executeUpdate()
                    return true
                }
            }
        }
        catch (e: Exception){
            throw SQLException("Something unexpected happened while trying to update groups data.")
        }
    }
}
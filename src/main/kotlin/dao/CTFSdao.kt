package dao

import ds.connection.ConnectionManager
import entity.CTFS
import interfaces.ICTFdao
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

class CTFSdao(): ICTFdao{

    override fun insertCTF(ctf: CTFS, connection: Connection): CTFS? {
        val sql = "INSERT INTO CTFS (CTFID, GRUPOID, PUNTUACION) VALUES (?, ?, ?)"

        try {

            connection.prepareStatement(sql)?.use { statement ->
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
        }catch (e: Exception){
            throw SQLException("There was an error while trying to connect to the database.")
        }
        return null
    }

    override fun getCTFParticipation(grupoId: Int, ctfId: Int, connection: Connection): CTFS? {
        val sql = "SELECT * FROM CTFS WHERE GRUPOID = ? AND CTFID = ?"

        try {

            connection.prepareStatement(sql)?.use { statement ->
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
        }catch (e: Exception){
            throw SQLException("There was an error while trying to connect to the database.")
        }
        return null
    }


    override fun getAllCTFSById(ctfId: Int, connection: Connection): List<CTFS>? {
        val sql = "SELECT * FROM CTFS WHERE GRUPOID = ? ORDER BY PUNTUACION DESC"


        try {

            connection.prepareStatement(sql)?.use { statement ->
                statement.setInt(1, ctfId)
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
        }catch (e: Exception){
            throw SQLException("There was an error while trying to connect to the database.")
        }
        return null
    }


    override fun updateCTFS(ctf: CTFS, connection: Connection): CTFS? {
        val sql = "UPDATE CTFS SET PUNTUACION = ? WHERE CTFID = ? AND GRUPOID = ?"

        try {

            connection.prepareStatement(sql)?.use { statement ->
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
        } catch (e: Exception){
            throw SQLException("There was an error while trying to connect to the database.")
        }
        return null
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int, connection: Connection): Boolean {
        val sql = "DELETE FROM CTFS WHERE CTFID = ? AND GRUPOID = ?"

        try {
                connection.prepareStatement(sql)?.use { statement ->
                    statement.setInt(1, ctfId)
                    statement.setInt(2, grupoId)
                    statement.executeUpdate()
                    return true
                }
        }
        catch (e: Exception){
            throw SQLException("Something unexpected happened while trying to delete participation data.")
        }
        return false
    }
}
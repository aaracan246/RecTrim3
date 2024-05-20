package dao

import entity.CTFS
import interfaces.ICTFdao
import java.sql.SQLException
import javax.sql.DataSource

class CTFSdao(private val dataSource: DataSource): ICTFdao{

    override fun insertCTF(ctf: CTFS): CTFS? {
        val sql = "INSERT INTO CTFS (ID, GRUPOID, PUNTUACION)"

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

    override fun getAllCTFS(): List<CTFS>? {
        val sql = "SELECT * FROM CTFS"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                val rs = statement.executeQuery()
                val ctfs = mutableListOf<CTFS>()
                while (rs!!.next()){
                    ctfs.add(
                        CTFS(
                            CTFid = rs.getInt("CTF_ID"),
                            grupoid = rs.getInt("GROUP_DESC"),   // Quizás recuperar ID - checkear DB
                            puntuacion = rs.getInt("BEST_GROUP_POSITION")
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

    override fun getCTFById(id: Int): CTFS? {
        val sql = "SELECT * FROM CTFS WHERE ID = (?)"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, id.toString())
                val rs = statement.executeQuery()
                if (rs.next()) {
                    return CTFS(
                        CTFid = rs.getInt("CTF_ID"),
                        grupoid = rs.getInt("GROUP_DESC"),   // Quizás recuperar ID - checkear DB
                        puntuacion = rs.getInt("BEST_GROUP_POSITION")
                    )
                }
                else{
                    throw SQLException("Something unexpected happened while trying to fetch group data.")
                }
            }
        }
    }

    override fun updateCTFS(ctf: CTFS): CTFS? {
        val sql = "UPDATE CTFS SET ID = ?, GRUPOID = ?, PUNTUACION = ?"

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
        val sql = "DELETE FROM CTFS WHERE ID = (?)"
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
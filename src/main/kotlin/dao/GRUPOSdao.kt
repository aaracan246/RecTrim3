package dao

import entity.GRUPOS
import interfaces.IGRUPOSdao
import java.sql.SQLException
import javax.sql.DataSource

class GRUPOSdao(private val dataSource: DataSource): IGRUPOSdao {

    override fun insertGroup(grupos: GRUPOS): GRUPOS? {
        val sql = "INSERT INTO GRUPOS (GRUPODESC, MEJORPOSCTFID) VALUES (?, ?)"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, grupos.grupoDesc)
                if (grupos.mejorPosCTFId != null){
                    statement.setInt(2, grupos.mejorPosCTFId)
                }
                else{
                    statement.setNull(2, java.sql.Types.INTEGER)
                }
                val rs = statement.executeUpdate()
                if (rs == 1) {
                    return grupos
                } else {
                    throw SQLException("Something unexpected happened while trying to insert the data.")
                }
            }
        }
    }

    override fun getAllGroups(): List<GRUPOS>? {
        val sql = "SELECT * FROM GRUPOS"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                val rs = statement.executeQuery()
                val grupos = mutableListOf<GRUPOS>()
                while (rs!!.next()){
                    grupos.add(
                        GRUPOS(
                            grupoId = rs.getInt("GRUPOID"),
                            grupoDesc = rs.getString("GRUPODESC"),   // Quizás recuperar ID - checkear DB
                            mejorPosCTFId = rs.getInt("MEJORPOSCTFID")
                        )
                    )
                }
                if (grupos != null){
                    return grupos
                }
                else{
                    throw SQLException("Something unexpected happened while trying to retrieve groups data.")
                }
            }
        }
    }

    override fun getGroupById(id: Int): GRUPOS? {
        val sql = "SELECT * FROM GRUPOS WHERE GRUPOID = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, id.toString())
                val rs = statement.executeQuery()
                if (rs.next()) {
                    return GRUPOS(
                        grupoId = rs.getInt("GRUPOID"),
                        grupoDesc = rs.getString("GRUPODESC"),   // Quizás recuperar ID - checkear DB
                        mejorPosCTFId = rs.getInt("MEJORPOSCTFID")
                    )
                }
                else{
                    throw SQLException("Something unexpected happened while trying to fetch group data.")
                }
            }
        }
    }

    override fun updateGroups(grupos: GRUPOS): GRUPOS? {
        val sql = "UPDATE GRUPOS SET GRUPODESC = ?, MEJORPOSCTFID = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, grupos.grupoId.toString())
                statement.setString(2, grupos.grupoDesc)
                grupos.mejorPosCTFId?.let { statement.setInt(3, it) }
                statement.executeUpdate()
                if (grupos != null){
                    return grupos
                }else{
                    throw SQLException("Something unexpected happened while trying to update groups data.")
                }
            }
        }
    }

    override fun deleteGroup(id: Int): Boolean {
        val sql = "DELETE FROM GRUPOS WHERE GRUPOID = ?"
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setInt(1, id)
                    (statement.executeUpdate() == 1)
                    return true
                }
            }
        }
        catch (e: Exception){
            throw SQLException("Something unexpected happened while trying to delete groups data.")
        }
    }

    override fun updateBestPosCTF(grupoId: Int, CTFId: Int): Boolean {
        val sql = "UPDATE GRUPOS SET SET MEJORPOSCTFID = ? WHERE GRUPOID = ?"

        return try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setInt(1, CTFId)
                    statement.setInt(2, grupoId)
                    statement.executeUpdate() > 0
                }
            }
        } catch (e: SQLException) {
        throw SQLException("Something unexpected happened while trying to update the best position for group $grupoId.") }
    }
}
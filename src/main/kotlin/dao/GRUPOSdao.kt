package dao

import ds.connection.ConnectionManager
import entity.GRUPOS
import interfaces.IGRUPOSdao
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

class GRUPOSdao(): IGRUPOSdao {

    override fun insertGroup(grupos: GRUPOS, connection: Connection): GRUPOS? {
        val sql = "INSERT INTO GRUPOS (GRUPODESC, MEJORPOSCTFID) VALUES (?, ?)"

        return try {

            connection.prepareStatement(sql)?.use { statement ->
                statement.setString(1, grupos.grupoDesc)
                if (grupos.mejorPosCTFId != null) {
                    statement.setInt(2, grupos.mejorPosCTFId)
                } else {
                    statement.setNull(2, java.sql.Types.INTEGER)
                }

                val rs = statement.executeUpdate()

                if (rs == 1) {
                    return grupos
                } else {
                    throw SQLException("Something unexpected happened while trying to insert the data.")
                }
            }

        } catch (e: Exception){
            throw SQLException("There was an error trying to connect to the database.")
        }
    }

    override fun getAllGroups(connection: Connection): List<GRUPOS>? {
        val sql = "SELECT * FROM GRUPOS"

        return try {

            connection.prepareStatement(sql)?.use { statement ->
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
        } finally {
            connection.close()
        }
    }

    override fun getGroupById(id: Int, connection: Connection): GRUPOS? {
        val sql = "SELECT * FROM GRUPOS WHERE GRUPOID = ?"

        try {

            connection.prepareStatement(sql)?.use { statement ->
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
                    return null
                }
            }
        }catch (e: Exception){
            throw SQLException("There was an error trying to connect to the database.")
        }
        return null
    }

    override fun updateGroups(grupos: GRUPOS, connection: Connection): GRUPOS? {
        val sql = "UPDATE GRUPOS SET GRUPODESC = ?, MEJORPOSCTFID = ?"


        try {

            connection.prepareStatement(sql)?.use { statement ->
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
        }catch (e: Exception){
            throw SQLException("There was an error trying to connect to the database.")
        }
        return null
    }

    override fun deleteGroup(id: Int, connection: Connection): Boolean {
        val sql = "DELETE FROM GRUPOS WHERE GRUPOID = ?"

        try {
                connection.prepareStatement(sql)?.use { statement ->
                    statement.setInt(1, id)
                    (statement.executeUpdate() == 1)
                    return true
                }
        }
        catch (e: Exception){
            throw SQLException("Something unexpected happened while trying to delete groups data.")
        }
        return false
    }

    override fun updateBestPosCTF(grupoId: Int, CTFId: Int?, connection: Connection): Boolean {
        val sql = "UPDATE GRUPOS SET MEJORPOSCTFID = ? WHERE GRUPOID = ?"

        return try {
                connection.prepareStatement(sql).use { statement ->
                    if (CTFId != null) {
                        statement?.setInt(1, CTFId)
                    } else {
                        statement?.setNull(1, java.sql.Types.INTEGER)
                    }
                    statement?.setInt(2, grupoId)
                    statement?.executeUpdate()!! > 0
                }
        } catch (e: SQLException) {
        throw SQLException("Something unexpected happened while trying to update the best position for group $grupoId.") }
    }
}
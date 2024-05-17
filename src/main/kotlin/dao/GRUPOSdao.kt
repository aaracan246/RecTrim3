package dao

import entity.GRUPOS
import interfaces.IGROUPSdao
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

class GRUPOSdao(private val dataSource: DataSource): IGROUPSdao {  // la consola da problemas en DAO !!!!!!!!!!
    override fun createGroup(grupos: GRUPOS): GRUPOS? {
        val sql = "INSERT INTO GRUPOS (ID, GRUPODESC, MEJORPOSCTFID)"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, grupos.grupoId.toString())
                statement.setString(2, grupos.grupoDesc)
                statement.setInt(3, grupos.mejorPosCTFId)
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
                            grupoDesc = rs.getString("GROUP_DESC"),   // Quizás recuperar ID - checkear DB
                            mejorPosCTFId = rs.getInt("BEST_GROUP_POSITION")
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
        val sql = "SELECT * FROM GRUPOS WHERE ID = (?)"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, id.toString())
                val rs = statement.executeQuery()
                if (rs.next()) {
                    return GRUPOS(
                        grupoId = rs.getInt(),
                        grupoDesc = rs.getString("GROUP_DESC"),   // Quizás recuperar ID - checkear DB
                        mejorPosCTFId = rs.getInt("BEST_GROUP_POSITION")
                    )
                }
                else{
                    throw SQLException("Something unexpected happened while trying to fetch group data.")
                }
            }
        }
    }

    override fun updateGroups(grupos: GRUPOS): GRUPOS? {
        val sql = "UPDATE GRUPOS SET ID = ?, GRUPODESC = ?, MEJORPOSCTFID = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, grupos.grupoId.toString())
                statement.setString(2, grupos.grupoDesc)
                statement.setInt(3, grupos.mejorPosCTFId)
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
        val sql = "DELETE FROM GRUPOS WHERE ID = (?)"
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
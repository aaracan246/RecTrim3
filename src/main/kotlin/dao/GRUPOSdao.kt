package dao

import ds.DataSourceFactory
import entity.GRUPOS
import inputOutput.Console
import interfaces.IGROUPSdao
import java.util.*
import javax.sql.DataSource

class GRUPOSdao(private val dataSource: DataSource, private val console: Console): IGROUPSdao {
    override fun createGroup(grupos: GRUPOS): GRUPOS? {
        val sql = "INSERT INTO GRUPOS (ID, GRUPODESC, MEJORPOSCTFID)"


    }

    override fun getAllGroups(): List<GRUPOS>? {
        TODO("Not yet implemented")
    }

    override fun getGroupById(id: UUID): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun updateGroups(group: GRUPOS): GRUPOS? {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(id: UUID): Boolean {
        TODO("Not yet implemented")
    }
}
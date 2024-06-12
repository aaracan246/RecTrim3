package ds.connection

import ds.DataSourceFactory
import java.sql.Connection
import javax.sql.DataSource

class ConnectionManager(private val dataSource: DataSource) {

    fun getConnection(): Connection?{
        var connection: Connection? = dataSource.connection

        if (connection == null || connection.isClosed){
            connection = dataSource.connection
        }
        return connection
    }

    fun closeConnection(){
        val connection: Connection? = dataSource.connection

        if (connection?.isClosed == false){
            connection.close()
        }
    }

    fun beginTransaction(){
        getConnection()?.autoCommit = false
    }

    fun commitTransaction(){
        getConnection()?.commit()
        getConnection()?.autoCommit = true
    }

    fun rollbackTransaction(){
        getConnection()?.rollback()
        getConnection()?.autoCommit = true
    }
}
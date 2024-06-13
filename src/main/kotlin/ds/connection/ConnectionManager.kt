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

    fun closeConnection(connection: Connection){

        if (!connection.isClosed){
            connection.close()
        }
    }

    fun beginTransaction(connection: Connection){
        connection.autoCommit = false
        connection.commit()
    }

    fun commitTransaction(connection: Connection){
        connection.commit()
        connection.autoCommit = true
    }

    fun rollbackTransaction(connection: Connection){
        connection.rollback()
        connection.autoCommit = true
    }
}
package dao

import dao.enumClass.DAOType
import ds.connection.ConnectionManager
import interfaces.ICTFdao
import interfaces.IGRUPOSdao
import java.io.File
import javax.sql.DataSource

/**
 * Esta clase gestiona los DAOs que vayan a ser utilizados
 * */
class DAOFactory {
    fun getDAO(connectionManager: ConnectionManager): Pair<ICTFdao?, IGRUPOSdao?>{
        val readType = readType(File("configSQL.txt"))
        return when(readType) {
            DAOType.DATASOURCE.desc -> Pair(CTFSdao(), GRUPOSdao())

            DAOType.XML.desc -> TODO()

            DAOType.JSON.desc -> TODO()

            else -> return Pair(null, null)
        }
    }

    /**
     * Esta función implementa la funcionalidad de leer el fichero inicial
     * */
    fun readType(filepath: File): String {
        val file = filepath.readLines()

        file.forEach{
            if (it.contains("tipo=")){
                val lineaSpliteada = it.split("=")

                return lineaSpliteada[1]
            }
        }
        return ""
    }
}
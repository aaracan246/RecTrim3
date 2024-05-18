package ds

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DataSourceFactory {
    enum class DataSourceType {
        HIKARI,
        JDBC,
        JSON,
        XML

    }

    fun getDS(dataSourceType: DataSourceType): DataSource {
        return when (dataSourceType) {
            DataSourceType.HIKARI -> {
                val config = HikariConfig()
                config.jdbcUrl = "jdbc:h2:./CTFSDAO"
                config.username = "user"
                config.password = "user"
                config.driverClassName = "org.h2.Driver"
                config.maximumPoolSize = 10
                config.isAutoCommit = true
                config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                HikariDataSource(config)
            }

            DataSourceType.JDBC -> TODO()


            DataSourceType.JSON -> TODO()


            DataSourceType.XML -> TODO()
        }
    }
}
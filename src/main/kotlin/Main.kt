import androidx.compose.ui.window.application
import dao.DAOFactory
import ds.DataSourceFactory
import ds.connection.ConnectionManager
import files.FileManager
import gui.GUI
import inputOutput.Console
import inputOutput.InputReceiver
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import viewmodel.ViewModel


fun main(args: Array<String>) = application{

    val console = Console()

    val args: Array<String> = arrayOf("-p", "23", "23", "150")

    val daoFactory = DAOFactory()

    val dataSourceHikari = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    val connectionManager = ConnectionManager(dataSourceHikari)

    val (ctfsdao, gruposdao) = daoFactory.getDAO(connectionManager)

    val gruposService = GRUPOSImpl(gruposdao)

    val ctfsService = CTFSImpl(ctfsdao)

    val inputReceiver = InputReceiver(console, gruposService, ctfsService, connectionManager)

    val viewModel = ViewModel(gruposService, connectionManager.getConnection())

    if (args.isNotEmpty() && args[0] == "-i"){
        GUI(gruposService, viewModel).App(::exitApplication)
    }

    inputReceiver.inputMenu(args)
}

import androidx.compose.ui.window.application
import dao.DAOFactory
import ds.DataSourceFactory
import files.FileManager
import gui.GUI
import inputOutput.Console
import inputOutput.InputReceiver
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import viewmodel.ViewModel


fun main(args: Array<String>) = application{

    val console = Console()


    val daoFactory = DAOFactory()


    val args: Array<String> = arrayOf("-i")

    val dataSourceHikari = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    val (ctfsdao, gruposdao) = daoFactory.getDAO(dataSourceHikari)
    //val gruposDAO = GRUPOSdao(dataSourceHikari)
    val gruposService = GRUPOSImpl(gruposdao)

    //val ctfs = CTFSdao(dataSourceHikari)
    val ctfsService = CTFSImpl(ctfsdao)

    val inputReceiver = InputReceiver(console, gruposService, ctfsService)
    val fileManager = FileManager(console, inputReceiver)
    val viewModel = ViewModel(gruposService)

    if (args.isNotEmpty() && args[0] == "-i"){
        GUI(gruposService, viewModel).App(::exitApplication)
    }

    inputReceiver.inputMenu(args)
}

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dao.CTFSdao
import dao.GRUPOSdao
import ds.DataSourceFactory
import files.FileManager
import inputOutput.Console
import inputOutput.InputReceiver
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl
import javax.xml.crypto.Data

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() {//= application {

    val console = Console()

    val arg: Array<String> = arrayOf("-e", "1", "2")

    val dataSourceHikari = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    val gruposDAO = GRUPOSdao(dataSourceHikari)
    val gruposService = GRUPOSImpl(gruposDAO)

    val ctfs = CTFSdao(dataSourceHikari)
    val ctfsService = CTFSImpl(ctfs)

    val inputReceiver = InputReceiver(console, gruposService, ctfsService)
    val fileManager = FileManager(console, inputReceiver)

    inputReceiver.inputMenu(arg)


//    Window(onCloseRequest = ::exitApplication) {
//        App()
//    }
}

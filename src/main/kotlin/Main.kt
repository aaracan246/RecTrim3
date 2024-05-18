import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dao.CTFSdao
import dao.GRUPOSdao
import ds.DataSourceFactory
import inputOutput.Console
import interfaces.services.GRUPOSService
import servicesImplementation.CTFSImpl
import servicesImplementation.GRUPOSImpl

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

    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    val gruposDAO = GRUPOSdao(dataSource)
    val gruposService = GRUPOSImpl(gruposDAO)

    val ctfs = CTFSdao(dataSource)
    val ctfsService = CTFSImpl(ctfs)




//    Window(onCloseRequest = ::exitApplication) {
//        App()
//    }
}

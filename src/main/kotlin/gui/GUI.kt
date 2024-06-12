package gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import servicesImplementation.GRUPOSImpl
import viewmodel.ViewModel

class GUI(private val gruposImpl: GRUPOSImpl, private val viewModel: ViewModel) {

    @Composable
    @Preview

    fun App(onExitApp: () -> Unit) {

            Window(onCloseRequest = onExitApp) {

                var filterText by remember { mutableStateOf("") }
                var filteredGroups by remember { mutableStateOf(viewModel.grupos) }


                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LazyColumn(modifier = Modifier
                                            .border(1.dp, color = Color.Black)
                                            .background(color = Color.Cyan)
                                            .fillMaxWidth()) {
                        itemsIndexed(filteredGroups){index, grupo ->
                            Row(horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()) {
                            Text("${grupo.grupoId} ${grupo.grupoDesc} ${grupo.mejorPosCTFId}", fontSize = 20.sp)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = filterText,
                        onValueChange = { filterText = it },
                        label = { Text("Search by group name") }
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = { filteredGroups = viewModel.filter(filterText)}) {
                            Text("Show")
                        }

                        Button(onClick = {
                            val filePath = "logs.txt"
                            viewModel.exportar(filePath, filteredGroups)
                        }
                        ) {
                            Text("Export")
                        }
                    }
                }
            }
        }
    }


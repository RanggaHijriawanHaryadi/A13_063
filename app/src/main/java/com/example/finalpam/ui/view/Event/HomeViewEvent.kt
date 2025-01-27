package com.example.finalpam.ui.view.Event

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalpam.R
import com.example.finalpam.entitas.Event
import com.example.finalpam.ui.viewmodel.Evnt.HomeUiEventState




@Composable
fun HomeEvntStatus(
    homeUiEventState: HomeUiEventState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {},
    onDetailClick: (Int) -> Unit
){
    when (homeUiEventState) {
        is HomeUiEventState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiEventState.Success ->
            if (homeUiEventState.event.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Event" )
                }
            } else {
                EventLayout(
                    event = homeUiEventState.event, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_event)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiEventState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun OnLoading(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.l),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.e), contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun EventLayout(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onDetailClick: (Event) -> Unit,
    onDeleteClick: (Event) -> Unit = {}
) {
    LazyColumn (
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(event) { event ->
            EventCard(
                event = event,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(event) },
                onDeleteClick = {
                    onDeleteClick(event)
                }
            )
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(event)
            },
            onDeleteCancel = {
                showDialog = false
            }
        )
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(18.dp)
            )
            Column (
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    DetailEvnt(judul = "Id_Event", isinya = event.id_event.toString())
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    text = event.nama_event,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "tanggal: ${event.tanggal_event}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "lokasi: ${event.lokasi_event}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun DetailEvnt(
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Row{
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = isinya,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { /*Do nothing*/ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data event?") },
        dismissButton = {
            TextButton(onClick = { onDeleteCancel() }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteConfirm() }) {
                Text(text = "Yes")
            }
        }
    )
}
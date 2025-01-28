package com.example.finalpam.ui.view.Tikets


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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalpam.R
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiHomeTkts
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import com.example.finalpam.ui.viewmodel.Tkts.HomeUiTikesState
import com.example.finalpam.ui.viewmodel.Tkts.HomeViewModelTkts


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewTikets(
    navigateToTktsEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDetailTktsClick: (Int) -> Unit = {},
    viewModel: HomeViewModelTkts = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiHomeTkts.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = { viewModel.getTkts() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTktsEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket")
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        HomeTktsStatus (
            homeUiTikesState = viewModel.tktsUIState,
            retryAction = { viewModel.getTkts() },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), // Pastikan ini ada
            onDetailTktsClick = onDetailTktsClick,
            onDeleteClick = {
                viewModel.deleteTkts(it.id_tiket)
                viewModel.getTkts()
            }
        )
    }
}

@Composable
fun HomeTktsStatus(
    homeUiTikesState: HomeUiTikesState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tikets) -> Unit = {},
    onDetailTktsClick: (Int) -> Unit

){
    when (homeUiTikesState) {
        is HomeUiTikesState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiTikesState.Success ->
            if (homeUiTikesState.tikets.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tiket" )
                }
            } else {
                TiketsLayout(
                    tikets = homeUiTikesState.tikets, modifier = modifier.fillMaxWidth(),
                    onDetailTktsClick = {
                        onDetailTktsClick(it.id_tiket)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiTikesState.Error -> OnError(
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
fun TiketsLayout(
    tikets: List<Tikets>,
    modifier: Modifier = Modifier,
    onDetailTktsClick: (Tikets) -> Unit,
    onDeleteClick: (Tikets) -> Unit = {},
    viewModel: HomeViewModelTkts = viewModel(factory = PenyediaViewModel.Factory)
) {
    LazyColumn (
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tikets) { tikets ->
            TiketsCard(
                tikets = tikets,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailTktsClick(tikets) },
                onDeleteClick = {
                    onDeleteClick(tikets)
                },
                getListEvnt = viewModel.evntList,
                getListPstr = viewModel.pstrList
            )
        }
    }
}

@Composable
fun TiketsCard(
    tikets: Tikets,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tikets) -> Unit = {},
    getListPstr: List<Peserta>,
    getListEvnt: List<Event>
) {
    var namapsrt = getListPstr.find { it.id_peserta == tikets.id_peserta}?.nama_peserta ?: "Tidak ditemukan nama peserta"
    var namaevnt = getListEvnt.find { it.id_event == tikets.id_event}?.nama_event ?: "Tidak ditemukan nama Event"
    var namaevnttanggal = getListEvnt.find { it.id_event == tikets.id_event}?.tanggal_event ?: "Tidak ditemukan tanggal"
    var namaeventlokasi = getListEvnt.find {  it.id_event == tikets.id_event}?.lokasi_event ?: "Tidak ditemukan lokasi"
    val cardColors = if (tikets.kapasitas_tiket == 0) {
        println("Sold Out")
        Color.Red
    } else if (tikets.kapasitas_tiket in 1..50) {
        Color.Yellow
    } else {
        Color.Green
    }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(tikets)
            },
            onDeleteCancel = {
                showDialog = false
            }
        )
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = cardColors(containerColor = cardColors)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){ Icon(
            painter = painterResource(id = R.drawable.tiket),
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .padding(18.dp)
            )
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row (
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    DetailTiket(judul = "Id Tiket", isinya = tikets.id_tiket.toString())
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    text = "Nama Event: ${namaevnt}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Nama Peserta: ${namapsrt}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Tanggal Event: ${namaevnttanggal}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Lokasi Event: ${namaeventlokasi}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}

@Composable
fun DetailTiket(
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
        text = { Text("Apakah anda yakin ingin menghapus data tiket?") },
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
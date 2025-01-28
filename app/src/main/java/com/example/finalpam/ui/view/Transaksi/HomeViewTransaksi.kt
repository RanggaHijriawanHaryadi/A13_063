package com.example.finalpam.ui.view.Transaksi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalpam.R
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.entitas.Transaksi
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiHomeTkts
import com.example.finalpam.ui.navigation.DestinasiHomeTski
import com.example.finalpam.ui.view.Tikets.HomeTktsStatus
import com.example.finalpam.ui.view.Tikets.TiketsCard
import com.example.finalpam.ui.view.Tikets.TiketsLayout
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import com.example.finalpam.ui.viewmodel.Tkts.HomeUiTikesState
import com.example.finalpam.ui.viewmodel.Tkts.HomeViewModelTkts
import com.example.finalpam.ui.viewmodel.Tski.HomeUiTransaksiState
import com.example.finalpam.ui.viewmodel.Tski.HomeViewModelTski


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewTransaksi(
    navigateToTskiEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDetailTskiClick: (Int) -> Unit = {},
    viewModel: HomeViewModelTski = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiHomeTski.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = { viewModel.getTski() }
            )
        },
        bottomBar = {
            Column (modifier = Modifier.fillMaxWidth().padding(44.dp)) {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = navigateToTskiEntry,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp) // Memberikan jarak antara ikon dan teks
                        )
                        Text("Tambah Transaksi")
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        ){
            HomeTskiStatus  (
                homeUiTransaksiState = viewModel.tskiUIState,
                retryAction = { viewModel.getTski() },
                modifier = Modifier
                    .fillMaxSize(), // Pastikan ini ada
                onDetailTskiClick = onDetailTskiClick,
                onDeleteClick = {
                    viewModel.deleteTski(it.id_transaksi)
                    viewModel.getTski()
                }
            )
        }
    }
}

@Composable
fun HomeTskiStatus(
    homeUiTransaksiState: HomeUiTransaksiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {},
    onDetailTskiClick: (Int) -> Unit

){
    when (homeUiTransaksiState) {
        is HomeUiTransaksiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiTransaksiState.Success ->
            if (homeUiTransaksiState.transaksi.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Transaksi" )
                }
            } else {
                TransaksiLayout(
                    transaksi = homeUiTransaksiState.transaksi, modifier = modifier.fillMaxWidth(),
                    onDetailTskiClick = {
                        onDetailTskiClick(it.id_transaksi)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiTransaksiState.Error -> OnError(retryAction,
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
fun TransaksiLayout(
    transaksi: List<Transaksi>,
    modifier: Modifier = Modifier,
    onDetailTskiClick: (Transaksi) -> Unit,
    onDeleteClick: (Transaksi) -> Unit = {},
    viewModel: HomeViewModelTski = viewModel(factory = PenyediaViewModel.Factory)
) {
    LazyColumn (
        modifier = modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transaksi) { transaksi ->
            TransaksiCard(
                transaksi = transaksi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailTskiClick(transaksi) },
                onDeleteClick = {
                    onDeleteClick(transaksi)
                },
                getList = viewModel.tktsList,
                getListPstr = viewModel.pstrList,
                getListEvnt = viewModel.evntList
            )
        }
    }
}

@Composable
fun TransaksiCard(
    transaksi: Transaksi,
    modifier: Modifier = Modifier,
    getList: List<Tikets>,
    getListPstr: List<Peserta>,
    getListEvnt: List<Event>,
    onDeleteClick: (Transaksi) -> Unit = {}
) {
    var Tikets = getList.find { it.id_tiket == transaksi.id_tiket}?.id_tiket ?: "Tidak ditemukan harga tiket"
    var namapsrt = getListPstr.find { it.id_peserta == transaksi.id_peserta}?.nama_peserta ?: "Tidak ditemukan nama peserta"
    var namaevnt = getListEvnt.find { it.id_event == transaksi.id_event}?.nama_event ?: "Tidak ditemukan nama Event"
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(transaksi)
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.skin))
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){ Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(18.dp)
            )
                Column (
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Id Tiket: ${Tikets}",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }

                    }
                    Divider(
                        thickness = 4.dp,
                        modifier = Modifier.fillMaxWidth())

                    Text(
                        text = "Nama Event: ${namaevnt}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "Nama Peserta: ${namapsrt}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "tanggal: ${transaksi.tanggal_transaksi}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "harga: ${transaksi.jumlah_pembayaran}",
                        style = MaterialTheme.typography.titleMedium
                    )

                }
            }
        }
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
        text = { Text("Apakah anda yakin ingin menghapus data transaksi?") },
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
package com.example.finalpam.ui.view.Transaksi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalpam.R
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.entitas.Transaksi
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiDetailTkts
import com.example.finalpam.ui.navigation.DestinasiDetailTski
import com.example.finalpam.ui.view.Tikets.BodyDetailTkts
import com.example.finalpam.ui.view.Tikets.DetailTkts
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import com.example.finalpam.ui.viewmodel.Tkts.DetailViewModelTkts
import com.example.finalpam.ui.viewmodel.Tski.DetailUiTskiState
import com.example.finalpam.ui.viewmodel.Tski.DetailViewModelTski

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewTransaksi(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEditTskiClick: () -> Unit,
    viewModel: DetailViewModelTski = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailTski.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = {
                    viewModel.getTransaksiById()
                }
            )
        },
        bottomBar = {
            Column (modifier = Modifier.fillMaxWidth().padding(44.dp)) {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onEditTskiClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                    ){
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Update Transaksi")
                    }
                }
            }
        },
    ) { innerPadding ->

        BodyDetailTski(
            modifier = Modifier.padding(innerPadding),
            detailUiTskiState = viewModel.detailUiTskiState,
            retryAction = { viewModel.getTransaksiById() }
        )

    }
}

@Composable
fun BodyDetailTski(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiTskiState: DetailUiTskiState,
    viewModel: DetailViewModelTski = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailUiTskiState) {
        is DetailUiTskiState.Loading -> com.example.finalpam.ui.view.Tikets.OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiTskiState.Success -> {
            if (detailUiTskiState.transaksi.data.id_transaksi == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailTski(
                    transaksi = detailUiTskiState.transaksi.data,
                    modifier = modifier.padding(16.dp),
                    tskiList = viewModel.tktsList,
                    pstrList = viewModel.pstrList,
                    evntList = viewModel.evntList
                )
            }
        }

        is DetailUiTskiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailTski(
    tskiList: List<Tikets>,
    pstrList: List<Peserta>,
    evntList: List<Event>,
    modifier: Modifier = Modifier,
    transaksi: Transaksi,

    ) {
    var Tikets = tskiList.find { it.id_tiket == transaksi.id_tiket }?.id_tiket
        ?: "Tidak ditemukan harga tiket"
    var Event = evntList.find { it.id_event == transaksi.id_event }?.nama_event
        ?: "Tidak ditemukan nama event"
    var Peserta = pstrList.find { it.id_peserta == transaksi.id_peserta }?.nama_peserta
        ?: "Tidak ditemukan nama peserta"

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            colorResource(id = R.color.yellow)
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailTski(judul = "Id Tiket", isinya = Tikets.toString())
            DetailTski(judul = "Nama Event", isinya = Event)
            DetailTski(judul = "Nama Peserta", isinya = Peserta)
            DetailTski(judul = "Tanggal Transaksi", isinya = transaksi.tanggal_transaksi)
            DetailTski(judul = "Jumlah Pembayaran", isinya = transaksi.jumlah_pembayaran)
            DetailTski(judul = "Jumlah Tiket yang dibeli", isinya = transaksi.jumlah_tiket)
        }

    }
}


@Composable
fun DetailTski(
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
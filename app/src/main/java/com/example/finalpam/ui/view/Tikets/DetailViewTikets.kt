package com.example.finalpam.ui.view.Tikets

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
import androidx.compose.material3.CardDefaults.cardColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiDetailPstr
import com.example.finalpam.ui.navigation.DestinasiDetailTkts
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import com.example.finalpam.ui.viewmodel.Tkts.DetailUiTktsState
import com.example.finalpam.ui.viewmodel.Tkts.DetailViewModelTkts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewTikets(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEditTktsClick: () -> Unit,
    viewModel: DetailViewModelTkts = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailTkts.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = {
                    viewModel.getTiketById()
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
                        onClick = onEditTktsClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                    ){
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Update Tiket")
                    }
                }
            }
        },
    ) { innerPadding ->

        BodyDetailTkts(
            modifier = Modifier.padding(innerPadding),
            detailUiTktsState = viewModel.detailUiTktsState,
            retryAction = { viewModel.getTiketById() }
        )

    }
}

@Composable
fun BodyDetailTkts(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiTktsState: DetailUiTktsState,
    viewModel: DetailViewModelTkts = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailUiTktsState) {
        is DetailUiTktsState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiTktsState.Success -> {
            if (detailUiTktsState.tikets.data.id_tiket == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailTkts(
                   tikets = detailUiTktsState.tikets.data,
                    modifier = modifier.padding(16.dp),
                    pstrList = viewModel.pstrList,
                    evntList = viewModel.evntList
                )
            }
        }

        is DetailUiTktsState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}


@Composable
fun ItemDetailTkts(
    pstrList: List<Peserta>,
    evntList: List<Event>,
    modifier: Modifier = Modifier,
    tikets: Tikets,

    ){
    var Event = evntList.find { it.id_event == tikets.id_event }?.nama_event ?: "Tidak ditemukan nama event"
    var Peserta = pstrList.find { it.id_peserta ==  tikets.id_peserta }?.nama_peserta ?: "Tidak ditemukan nama peserta"
    var EventTgl = evntList.find { it.id_event == tikets.id_event }?.tanggal_event ?: "Tidak ditemukan tanggal Event"
    var EventLksi = evntList.find { it.id_event == tikets.id_event }?.lokasi_event ?: "Tidak ditemukan lokasi Event"
    val cardColors = if (tikets.kapasitas_tiket == 0) {
        println("Sold Out")
        Color.Red
    } else if (tikets.kapasitas_tiket in 1..50) {
        Color.Yellow
    } else {
        Color.Green
    }

        Card(
                modifier = modifier,
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),colors = cardColors(containerColor = cardColors)
            ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailTkts(judul = "Id Tiket", isinya = tikets.id_tiket.toString())
                DetailTkts(judul = "Nama Event", isinya = Event)
                DetailTkts(judul = "Nama Peserta", isinya = Peserta)
                DetailTkts(judul = "Tanggal Event", isinya = EventTgl)
                DetailTkts(judul = "Lokasi Event", isinya = EventLksi)
                DetailTkts(judul = "Harga tiket", isinya = tikets.harga_tiket)
                DetailTkts(judul = "Kapasitas tiket", isinya = tikets.kapasitas_tiket.toString())
            }
        }
}

@Composable
fun DetailTkts(
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
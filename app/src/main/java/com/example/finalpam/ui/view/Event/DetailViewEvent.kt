package com.example.finalpam.ui.view.Event

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
import androidx.compose.material.icons.filled.Add
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
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiDetailEvnt
import com.example.finalpam.ui.viewmodel.Evnt.DetailUiEvntState
import com.example.finalpam.ui.viewmodel.Evnt.DetailViewModelEvnt
import com.example.finalpam.ui.viewmodel.PenyediaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewEvent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    viewModel: DetailViewModelEvnt = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailEvnt.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = {
                    viewModel.getEventById()
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
                        onClick = onEditClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                    ){
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Update Event")
                    }
                }
            }
        },
    ) { innerPadding ->

        BodyDetailEvnt(
            modifier = Modifier.padding(innerPadding),
            detailUiEvntState = viewModel.detailUiEvntState,
            retryAction = { viewModel.getEventById() }
        )

    }
}

@Composable
fun BodyDetailEvnt(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiEvntState: DetailUiEvntState
) {
    when (detailUiEvntState) {
        is DetailUiEvntState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiEvntState.Success -> {
            if (detailUiEvntState.event.data.id_event == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailEvnt(
                    event = detailUiEvntState.event.data,
                    modifier = modifier.padding(16.dp)
                )
            }
        }

        is DetailUiEvntState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailEvnt(
    modifier: Modifier = Modifier,
    event: Event,

    ){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            colorResource(id = R.color.yellow))
    ){
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailEvent(judul = "Nama Event", isinya = event.nama_event)
            DetailEvent(judul = "Deskripsi Event", isinya = event.deskripsi_event)
            DetailEvent(judul = "Tangga Event", isinya = event.tanggal_event)
            DetailEvent(judul = "Lokasi Event", isinya = event.lokasi_event)
        }
    }
}

@Composable
fun DetailEvent(
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
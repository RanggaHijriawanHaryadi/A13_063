package com.example.finalpam.ui.view.peserta

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
import androidx.compose.material3.CardColors
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
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiDetailPstr
import com.example.finalpam.ui.viewmodel.Pstr.DetailUiPstrState
import com.example.finalpam.ui.viewmodel.Pstr.DetailViewModelPstr
import com.example.finalpam.ui.viewmodel.PenyediaViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewPstr(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEditPstrClick: () -> Unit,
    viewModel: DetailViewModelPstr = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailPstr.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = {
                    viewModel.getPesertaById()
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
                        onClick = onEditPstrClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                    ){
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Update Peserta")
                    }
                }
            }
        },
    ) { innerPadding ->

        BodyDetailPstr(
            modifier = Modifier.padding(innerPadding),
            detailUiPstrState = viewModel.detailUiPstrState,
            retryAction = { viewModel.getPesertaById() }
        )

    }
}

@Composable
fun BodyDetailPstr(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiPstrState: DetailUiPstrState
) {
    when (detailUiPstrState) {
        is DetailUiPstrState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiPstrState.Success -> {
            if (detailUiPstrState.peserta.data.id_peserta == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPstr(
                    peserta = detailUiPstrState.peserta.data,
                    modifier = modifier.padding(16.dp)
                )
            }
        }

        is DetailUiPstrState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun ItemDetailPstr(
    modifier: Modifier = Modifier,
    peserta: Peserta,

){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(
            colorResource(id = R.color.yellow))
    ){
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailPstr(judul = "Nama Peserta", isinya = peserta.nama_peserta)
            DetailPstr(judul = "Email", isinya = peserta.email)
            DetailPstr(judul = "Nomor Telepon", isinya = peserta.nomor_telepon)
        }
    }
}

@Composable
fun DetailPstr(
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
package com.example.finalpam.ui.view.peserta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalpam.R
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiHomePstr
import com.example.finalpam.ui.viewmodel.Pstr.HomeUiPesertaState
import com.example.finalpam.ui.viewmodel.Pstr.HomeViewModelPstr
import com.example.finalpam.ui.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewPeserta(
    navigateToPstrEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPstrClick: (Int) -> Unit = {},
    onBackClick: () -> Unit,
    viewModel: HomeViewModelPstr = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiHomePstr.titleRes,
                canNavigateBack = true,
                navigateUp = onBackClick,
                onRefresh = { viewModel.getPstr() }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(44.dp))
            {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = navigateToPstrEntry,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp) // Memberikan jarak antara ikon dan teks
                        )
                        Text("Tambah Peserta")
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

            HomePstrStatus(
                homeUiPesertaState = viewModel.pstrUIState,
                retryAction = { viewModel.getPstr() },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), // Pastikan ini ada
                onDetailPstrClick = onDetailPstrClick,
                onDeleteClick = {
                    viewModel.deletePstr(it.id_peserta)
                    viewModel.getPstr()
                }
            )


    }
}

@Composable
fun HomePstrStatus(
    homeUiPesertaState: HomeUiPesertaState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {},
    onDetailPstrClick: (Int) -> Unit
){
    when (homeUiPesertaState) {
        is HomeUiPesertaState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiPesertaState.Success ->
            if (homeUiPesertaState.peserta.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Peserta" )
                }
            } else {
                PesertaLayout(
                    peserta = homeUiPesertaState.peserta, modifier = modifier.fillMaxWidth(),
                    onDetailPstrClick = {
                        onDetailPstrClick(it.id_peserta)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiPesertaState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun PesertaLayout(
    peserta: List<Peserta>,
    modifier: Modifier = Modifier,
    onDetailPstrClick: (Peserta) -> Unit,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peserta) { peserta ->
            PesertaCard(
                peserta = peserta,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailPstrClick(peserta) },
                onDeleteClick = {
                    onDeleteClick(peserta)
                }
            )
        }
    }
}

@Composable
fun PesertaCard(
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(peserta)
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
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(18.dp)
            )
                Column (
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = peserta.nama_peserta,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }
                    }
                    Text(
                        text = peserta.email,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = peserta.nomor_telepon,
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
        text = { Text("Apakah anda yakin ingin menghapus data peserta?") },
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
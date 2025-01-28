package com.example.finalpam.ui.view.Transaksi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.ui.costumewidget.DropdownFestival
import com.example.finalpam.ui.costumewidget.TopAppBar

import com.example.finalpam.ui.navigation.DestinasiInsertTski
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import com.example.finalpam.ui.viewmodel.Tski.InsertUiTransaksiEvent
import com.example.finalpam.ui.viewmodel.Tski.InsertUiTransaksiState
import com.example.finalpam.ui.viewmodel.Tski.InsertViewModelTski
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertViewTransaksi(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelTski = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsertTski.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            InsertBodyTski(
                insertUiTransaksiState =  viewModel.uiStateTski,
                getList = viewModel.tktsList,
                getListPstr = viewModel.pstrList,
                getListEvnt = viewModel.evntList,
                onTskiValueChange = viewModel::updateInsertTskiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertTski()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun InsertBodyTski(
    insertUiTransaksiState: InsertUiTransaksiState,
    onTskiValueChange: (InsertUiTransaksiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    getList: List<Tikets>,
    getListPstr: List<Peserta>,
    getListEvnt: List<Event>
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputTski(
            insertUiTransaksiEvent = insertUiTransaksiState.insertUiTransaksiEvent,
            onValueChange = onTskiValueChange,
            modifier = Modifier.fillMaxWidth(),
            getList = getList,
            getListPstr = getListPstr,
            getListEvnt = getListEvnt,
            insertUiTransaksiState = insertUiTransaksiState
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputTski(
    insertUiTransaksiEvent: InsertUiTransaksiEvent,
    insertUiTransaksiState: InsertUiTransaksiState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiTransaksiEvent) -> Unit = {},
    getList: List<Tikets>,
    getListPstr: List<Peserta>,
    getListEvnt: List<Event>,
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ){
        DropdownFestival(
            selectedValue = insertUiTransaksiState.insertUiTransaksiEvent.id_tiket.toString(),
            options =  getList.map { it.id_tiket.toString() },
            label = "masukkan id Tiket ",
            onValueChangedEvent = { tikets ->
                onValueChange (insertUiTransaksiState.insertUiTransaksiEvent.copy(id_tiket = tikets.toInt()))
            }
        )
        DropdownFestival(
            selectedValue = getList.find { it.id_tiket == insertUiTransaksiState.insertUiTransaksiEvent.id_tiket }?.harga_tiket ?: "",
            options = getList.map { it.harga_tiket },
            label = "Masukkan harga tiket",
            onValueChangedEvent = { harga ->
                getList.find { it.harga_tiket == harga }?.let { selectedHarga ->
                    val jumlahTiket = insertUiTransaksiState.insertUiTransaksiEvent.jumlah_tiket.toDoubleOrNull() ?: 0.0
                    val totalPembayaran = jumlahTiket * (selectedHarga.harga_tiket.toDoubleOrNull() ?: 0.0)

                    onValueChange(insertUiTransaksiState.insertUiTransaksiEvent.copy(id_tiket = selectedHarga.id_tiket, jumlah_pembayaran = totalPembayaran.toString()))
                }
            }
        )

        DropdownFestival(
            selectedValue = getListPstr.find { it.id_peserta == insertUiTransaksiState.insertUiTransaksiEvent.id_peserta }?.nama_peserta?:"",
            options = getListPstr.map { it.nama_peserta },
            label = "Masukkan nama Peserta",
            onValueChangedEvent = { peserta ->
                val selectedpeserta = getListPstr.find { it.nama_peserta == peserta }
                selectedpeserta?.let {
                    onValueChange (insertUiTransaksiState.insertUiTransaksiEvent.copy(id_peserta = it.id_peserta))
                }
            }
        )
        DropdownFestival(
            selectedValue = getListEvnt.find { it.id_event == insertUiTransaksiState.insertUiTransaksiEvent.id_event }?.nama_event?:"",
            options = getListEvnt.map { it.nama_event },
            label = "Masukkan nama event",
            onValueChangedEvent = { event ->
                val selectedevent = getListEvnt.find { it.nama_event == event }
                selectedevent?.let {
                    onValueChange (insertUiTransaksiState.insertUiTransaksiEvent.copy(id_event = it.id_event))
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertUiTransaksiState.insertUiTransaksiEvent.jumlah_tiket,
            onValueChange = { jumlah ->
                // Update jumlah tiket
                val jumlahTiket = jumlah.toDoubleOrNull() ?: 0.0
                val hargaTiket = getList.find { it.id_tiket == insertUiTransaksiState.insertUiTransaksiEvent.id_tiket }?.harga_tiket?.toDoubleOrNull() ?: 0.0
                val totalPembayaran = jumlahTiket * hargaTiket

                // Update state dengan jumlah tiket dan total pembayaran baru
                onValueChange(
                    insertUiTransaksiState.insertUiTransaksiEvent.copy(
                        jumlah_tiket = jumlah,
                        jumlah_pembayaran = totalPembayaran.toString()
                    )
                )
            },
            label = { Text("Jumlah Tiket") },
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = insertUiTransaksiState.insertUiTransaksiEvent.jumlah_pembayaran,
            onValueChange = {},
            label = { Text("Jumlah pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = insertUiTransaksiEvent.tanggal_transaksi,
            onValueChange = {onValueChange(insertUiTransaksiEvent.copy(tanggal_transaksi = it))},
            label = { Text("Tanggal transaksi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,

        )
        if (enabled){
            Text(text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp))
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp))

    }
}
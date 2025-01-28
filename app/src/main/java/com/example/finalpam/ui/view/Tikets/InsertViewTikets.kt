package com.example.finalpam.ui.view.Tikets

import androidx.compose.foundation.layout.Arrangement
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
import com.example.finalpam.ui.navigation.DestinasiInsertTkts
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import com.example.finalpam.ui.viewmodel.Tkts.InsertUiTiketsEvent
import com.example.finalpam.ui.viewmodel.Tkts.InsertUiTiketsState
import com.example.finalpam.ui.viewmodel.Tkts.InsertViewModelTkts
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertViewTikets(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelTkts = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsertTkts.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        InsertBodyTkts(
            insertUiTiketState =  viewModel.uiStateTkts,
            getListEvnt = viewModel.evntList,
            getListPstr = viewModel.pstrList,
            onTktsValueChange = viewModel::updateInsertTktsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTkts()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InsertBodyTkts(
    insertUiTiketState: InsertUiTiketsState,
    onTktsValueChange: (InsertUiTiketsEvent) -> Unit,
    onSaveClick: () -> Unit,
    getListPstr: List<Peserta>,
    getListEvnt: List<Event>,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputTkts(
            insertUiTiketsEvent = insertUiTiketState.insertUiTiketsEvent,
            onValueChange = onTktsValueChange,
            getListPstr = getListPstr,
            getListEvnt = getListEvnt,
            insertUiTiketState = insertUiTiketState,
            modifier = Modifier.fillMaxWidth()
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
fun FormInputTkts(
    insertUiTiketsEvent: InsertUiTiketsEvent,
    insertUiTiketState: InsertUiTiketsState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiTiketsEvent) -> Unit = {},
    getListPstr: List<Peserta>,
    getListEvnt: List<Event>,
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        DropdownFestival(
            selectedValue = getListEvnt.find { it.id_event == insertUiTiketState.insertUiTiketsEvent.id_event }?.nama_event?:"",
            options = getListEvnt.map { it.nama_event },
            label = "Masukkan nama event",
            onValueChangedEvent = { event ->
                val selectedevent = getListEvnt.find { it.nama_event == event }
               selectedevent?.let {
                    onValueChange (insertUiTiketState.insertUiTiketsEvent.copy(id_event = it.id_event))
                }
            }
        )

        DropdownFestival(
            selectedValue = getListPstr.find { it.id_peserta == insertUiTiketState.insertUiTiketsEvent.id_peserta }?.nama_peserta?:"",
            options = getListPstr.map { it.nama_peserta },
            label = "Masukkan nama Peserta",
            onValueChangedEvent = { peserta ->
                val selectedpeserta = getListPstr.find { it.nama_peserta == peserta }
                selectedpeserta?.let {
                    onValueChange (insertUiTiketState.insertUiTiketsEvent.copy(id_peserta = it.id_peserta))
                }
            }
        )

        OutlinedTextField(
            value = insertUiTiketsEvent.kapasitas_tiket.toString(),
            onValueChange = {
                // Konversi dari String ke Int, gunakan nilai default jika tidak valid
                val newValue = it.toIntOrNull() ?: 0
                onValueChange(insertUiTiketsEvent.copy(kapasitas_tiket = newValue))
            },
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = insertUiTiketsEvent.harga_tiket,
            onValueChange = {onValueChange(insertUiTiketsEvent.copy(harga_tiket = it))},
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
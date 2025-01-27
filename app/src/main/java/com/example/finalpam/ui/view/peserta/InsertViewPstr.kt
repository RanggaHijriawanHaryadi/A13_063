package com.example.finalpam.ui.view.peserta

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
import com.example.finalpam.ui.costumewidget.TopAppBar
import com.example.finalpam.ui.navigation.DestinasiInsertPstr
import com.example.finalpam.ui.viewmodel.Pstr.InsertUiPesertaEvent
import com.example.finalpam.ui.viewmodel.Pstr.InsertUiPesertaState
import com.example.finalpam.ui.viewmodel.Pstr.InsertViewModelPstr
import com.example.finalpam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertViewPstr(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelPstr = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsertPstr.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        InsertBodyPstr(
            insertUiPesertaState =  viewModel.uiStatePstr,
            onPstrValueChange = viewModel::updateInsertPstrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPstr()
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
fun InsertBodyPstr(
    insertUiPesertaState: InsertUiPesertaState,
    onPstrValueChange: (InsertUiPesertaEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputPstr(
            insertUiPesertaEvent = insertUiPesertaState.insertUiPesertaEvent,
            onValueChange = onPstrValueChange,
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
fun FormInputPstr(
    insertUiPesertaEvent: InsertUiPesertaEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiPesertaEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertUiPesertaEvent.nama_peserta,
            onValueChange = {
                onValueChange(insertUiPesertaEvent.copy(nama_peserta = it))
                            },
            label = { Text("Nama Peserta") },
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPesertaEvent.email,
            onValueChange = {onValueChange(insertUiPesertaEvent.copy(email = it))},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = insertUiPesertaEvent.nomor_telepon,
            onValueChange = {onValueChange(insertUiPesertaEvent.copy(nomor_telepon = it))},
            label = { Text("Nomor Telepon") },
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
package com.example.finalpam.ui.view.Event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalpam.ui.viewmodel.Evnt.InsertUiEvntEvent




@Composable
fun FormInputEvnt(
    insertUiEvent: InsertUiEvntEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvntEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertUiEvent.nama_event,
            onValueChange = {
                onValueChange(insertUiEvent.copy(nama_event = it))
            },
            label = { Text("Nama Event") },
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_event,
            onValueChange = {onValueChange(insertUiEvent.copy(deskripsi_event = it))},
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,

        )
        OutlinedTextField(
            value = insertUiEvent.tanggal_event,
            onValueChange = {onValueChange(insertUiEvent.copy(tanggal_event = it))},
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        OutlinedTextField(
            value = insertUiEvent.lokasi_event,
            onValueChange = {onValueChange(insertUiEvent.copy(lokasi_event = it))},
            label = { Text("Lokasi Event") },
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
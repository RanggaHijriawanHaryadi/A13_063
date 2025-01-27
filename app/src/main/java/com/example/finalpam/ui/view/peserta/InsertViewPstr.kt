package com.example.finalpam.ui.view.peserta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.finalpam.ui.viewmodel.Pstr.InsertUiPesertaEvent





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
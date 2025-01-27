package com.example.finalpam.ui.view.Event


import androidx.compose.foundation.layout.Row

import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.sp




@Composable
fun DetailEvnt(
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Row{
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = isinya,
            fontSize = 20.sp
        )
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
        text = { Text("Apakah anda yakin ingin menghapus data event?") },
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
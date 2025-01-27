package com.example.finalpam.ui.view.Event

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.finalpam.entitas.Event





@Composable
fun ItemDetailEvnt(
    modifier: Modifier = Modifier,
    event: Event,

    ){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ){
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailEvent(judul = "Nama Event", isinya = event.nama_event)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailEvent(judul = "Deskripsi Event", isinya = event.deskripsi_event)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailEvent(judul = "Tangga Event", isinya = event.tanggal_event)
            Spacer(modifier = Modifier.padding(4.dp))
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
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
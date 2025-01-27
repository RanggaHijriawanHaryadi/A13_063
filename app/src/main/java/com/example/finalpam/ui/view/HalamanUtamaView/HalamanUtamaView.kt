package com.example.finalpam.ui.view.HalamanUtamaView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn



import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalpam.R


@Composable
fun HalamanUtamaView(
  onPesertaClick: () -> Unit,
  onEventClick: () -> Unit,
  onTiketsClick: ()-> Unit,
  onTransaksiClick: ()->Unit,
  modifier: Modifier = Modifier
){
      LazyColumn (
        modifier = Modifier.fillMaxSize()

      ) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(color = colorResource(id = R.color.skin),shape = RoundedCornerShape(bottomEnd = 70.dp))
              .padding(8.dp),
            contentAlignment = Alignment.Center
          ){

              Image(
                painter = painterResource(id = R.drawable.festifal),
                contentDescription = "",
                Modifier.size(140.dp)
              )

           }
        }

        item {
          Spacer(modifier = Modifier.height(50.dp))
        }


        item {

          Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = "Halaman Utama",
              fontSize = 24.sp,
              fontStyle = FontStyle.Italic,
              fontWeight = FontWeight.SemiBold
            )
            Box(
              modifier = Modifier.fillMaxWidth()
                .background(
                  colorResource(id = R.color.yellow),
                  shape = RoundedCornerShape(8.dp)
                ).padding(16.dp)
            )
            {
              Column(modifier = Modifier.fillMaxWidth()) {
                // Home Peserta
                Button(
                  onClick = onPesertaClick,
                  modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                  colors = ButtonDefaults.buttonColors(Color(0xFF87CEEB))
                ) {
                  Text(
                    text = "Home Peserta",
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic
                  )
                }

              }
            }
          }
        }
      }

}
package com.example.finalpam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalpam.ui.view.peserta.HomeViewPeserta


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePstr.route,
        modifier = Modifier
    ) {


        // Halaman Peserta
        composable(
            DestinasiHomePstr.route
        ) {
            HomeViewPeserta(
                navigateToPstrEntry = { navController.navigate(DestinasiInsertPstr.route) },
                onDetailPstrClick = { id_peserta->
                    navController.navigate("${DestinasiDetailPstr.route}/$id_peserta")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
package com.example.finalpam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalpam.ui.view.Event.DetailViewEvent
import com.example.finalpam.ui.view.Event.HomeViewEvent
import com.example.finalpam.ui.view.Event.InsertViewEvent
import com.example.finalpam.ui.view.Event.UpdateViewEvent
import com.example.finalpam.ui.view.HalamanUtamaView.HalamanUtamaView
import com.example.finalpam.ui.view.Tikets.DetailViewTikets
import com.example.finalpam.ui.view.peserta.DetailViewPstr
import com.example.finalpam.ui.view.peserta.HomeViewPeserta
import com.example.finalpam.ui.view.peserta.InsertViewPstr
import com.example.finalpam.ui.view.peserta.UpdateViewPstr
import com.example.finalpam.ui.view.Tikets.HomeViewTikets
import com.example.finalpam.ui.view.Tikets.InsertViewTikets
import com.example.finalpam.ui.view.Tikets.UpdateViewTikets
import com.example.finalpam.ui.view.Transaksi.DetailViewTransaksi
import com.example.finalpam.ui.view.Transaksi.HomeViewTransaksi
import com.example.finalpam.ui.view.Transaksi.InsertViewTransaksi
import com.example.finalpam.ui.view.Transaksi.UpdateViewTransaksi


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHalamanUtama.route,
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
        composable(DestinasiInsertPstr.route){
            InsertViewPstr(
                navigateBack = {
                navController.popBackStack()
            })
        }

        composable(
            DestinasiDetailPstr.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPstr.Id_Peserta){
                    type = NavType.IntType
                }
            )
        ) {
            val id_peserta = it.arguments?.getInt(DestinasiDetailPstr.Id_Peserta)
            id_peserta?.let {
                DetailViewPstr(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditPstrClick =  {
                        navController.navigate("${DestinasiUpdatePstr.route}/$id_peserta")
                                       },
                )
            }
        }
    }
}
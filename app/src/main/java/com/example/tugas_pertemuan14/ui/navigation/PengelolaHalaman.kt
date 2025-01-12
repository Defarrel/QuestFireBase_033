package com.example.tugas_pertemuan14.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugas_pertemuan14.ui.home.pages.DetailView
import com.example.tugas_pertemuan14.ui.home.pages.HomeScreen
import com.example.tugas_pertemuan14.ui.home.pages.InsertView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(route = DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsert.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }
        composable(route = DestinasiInsert.route) {
            InsertView(
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                onNavigate = {
                    navController.navigate(DestinasiHome.route)
                },
            )
        }
        composable(
            DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM){
                    type = NavType.StringType
                }
            )
        ){
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let {
                DetailView(
                    onBack = {
                        navController.navigate(DestinasiHome.route)
                    },
                    navigateToEdit =  {
/*                        navController.navigate("${DestinasiUpdate.route}/$it")*/
                    }
                )
            }
        }
    }
}
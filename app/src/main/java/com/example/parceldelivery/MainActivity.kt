package com.example.parceldelivery


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.parceldelivery.ui.theme.ParcelDeliveryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcelDeliveryTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            MainNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Calculate,
        BottomNavItem.Shipment,
        BottomNavItem.Profile
    )
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar(containerColor = Color.White) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.label) },
                modifier = Modifier.size(24.dp),
                label = { Text(item.label) },

            )
        }
    }
}

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Calculate.route) {   Calculate(onBackClick = { navController.popBackStack() }) }
        composable(BottomNavItem.Shipment.route) { ShipmentScreen(onBackClick = { navController.popBackStack() }) }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}

sealed class BottomNavItem(val route: String, @DrawableRes val icon: Int, val label: String) {
    object Home : BottomNavItem("home",R.drawable.home, "Home")
    object Calculate : BottomNavItem("calculate", R.drawable.keys, "Calculate")
    object Shipment : BottomNavItem("shipment", R.drawable.restore, "Shipment")
    object Profile : BottomNavItem("profile", R.drawable.user, "Profile")
}




@Composable
fun ProfileScreen() {
    Box(Modifier.fillMaxSize()) {
        Text("Profile Screen")
    }
}

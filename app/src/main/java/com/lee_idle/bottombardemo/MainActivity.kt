package com.lee_idle.bottombardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lee_idle.bottombardemo.screens.Contacts
import com.lee_idle.bottombardemo.screens.Favorites
import com.lee_idle.bottombardemo.screens.Home
import com.lee_idle.bottombardemo.ui.theme.BottomBarDemoTheme
//import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomBarDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Bottom Navigation Demo") }) },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)){
                NavigationHost(navController = navController)
            }},
        bottomBar = { BottomNavigationBar(navController = navController) },
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route){
            Home()
        }

        composable(NavRoutes.Contacts.route) {
            Contacts()
        }

        composable(NavRoutes.Favorites.route) {
            Favorites()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    /*
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
    }
     */
    // BottomNavigation
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItem.forEach{navItem ->
            // BottomNavigationItem
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                          navController.navigate(navItem.route){
                              popUpTo(navController.graph.findStartDestination().id){
                                  saveState = true
                              }
                              launchSingleTop = true
                              restoreState = true
                          } },
                icon = {
                    Icon(imageVector = navItem.image,
                        contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BottomBarDemoTheme {
        MainScreen()
    }
}
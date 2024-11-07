package com.example.enishop.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun TitleApp(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shop",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ENI-SHOP",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 40.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    drawerState: DrawerState,
    onLogout: () -> Unit,
    darkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val currentDestination = navController.currentBackStackEntry?.destination?.route

    TopAppBar(
        colors = topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { TitleApp() },
        navigationIcon = {
            if (currentDestination == "articleList") {
                IconButton(onClick = { navController.navigate("articleList") }) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                }
            } else {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            ThemeSwitchIcon(darkTheme = darkTheme, onThemeToggle = onThemeToggle)
        }
    )
}

@Composable
fun ThemeSwitchIcon(darkTheme: Boolean, onThemeToggle: (Boolean) -> Unit) {
    IconButton(onClick = {
        onThemeToggle(!darkTheme)
    }) {
        Icon(
            imageVector = if (darkTheme) Icons.Filled.Settings else Icons.Filled.Home,
            contentDescription = "Switch Theme"
        )
    }
}

@Composable
fun DrawerContent(navController: NavController, onLogout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(232.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Text(
                text = "Menu",
                modifier = Modifier.padding(bottom = 24.dp),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            MenuItem(
                text = "Home",
                icon = Icons.Filled.Home,
                onClick = { navController.navigate("home") }
            )
            MenuItem(
                text = "Articles",
                icon = Icons.Filled.ShoppingCart,
                onClick = { navController.navigate("articleList") }
            )
            MenuItem(
                text = "Account",
                icon = Icons.Filled.AccountCircle,
                onClick = { navController.navigate("account") }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Logout",
                modifier = Modifier
                    .clickable {
                        onLogout()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}

@Composable
fun MenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 4.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

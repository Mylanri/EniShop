package com.example.enishop.ui.screen.Login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.enishop.dao.memory.UserDAOMemoryImpl
import com.example.enishop.ui.theme.EniShopTheme

@Composable
fun LoginScreen(
    navController: NavController,
    userDAO: UserDAOMemoryImpl,
    onLoginSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenue sur ENI-SHOP",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val user = userDAO.findByNameAndPassword(name, password)
                if (user != null) {
                    onLoginSuccess()
                } else {
                    errorMessage = "Invalid username or password"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {
                val user = userDAO.findByNameAndPassword(name, password)
                if (user != null) {
                    onLoginSuccess()
                } else {
                    errorMessage = "Invalid username or password"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    EniShopTheme {
        LoginScreen(
            navController = rememberNavController(),
            userDAO = UserDAOMemoryImpl(),
            onLoginSuccess = {}
        )
    }
}
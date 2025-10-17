package com.example.userinformation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.userinformation.utils.AppTopBar
import com.example.userinformation.viewmodel.UserViewModel
import com.example.userinformation.viewmodel.UsersUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int,
    vm: UserViewModel
) {
    val state by vm.uiState.collectAsState()
    val user = (state as? UsersUiState.Success)?.users?.find { it.id == userId }

    Scaffold(
        topBar = {
            AppTopBar(title = user?.name ?: "User Detail")
        }
    ) { paddingValues ->
        if (user == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("User not found or loading...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.photo),
                    contentDescription = user.name,
                    modifier = Modifier.size(150.dp).padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Company:")
                Text(user.company, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                Text("Email:")
                Text(user.email, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                Text("Phone:")
                Text(user.phone, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                Text("Address:")
                Text(user.address, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                Text("State:")
                Text(user.state, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                Text("Country:")
                Text(user.country, fontWeight = FontWeight.Bold)
            }
        }
    }
}


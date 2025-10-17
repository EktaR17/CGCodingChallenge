package com.example.userinformation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.userinformation.viewmodel.UserViewModel
import com.example.userinformation.viewmodel.UsersUiState
import com.example.network.model.User
import com.example.userinformation.utils.AppTopBar

@Composable
fun UserListScreen(
    vm: UserViewModel,
    onUserClick: (User) -> Unit
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "User List") }
    ) { paddingValues ->

        when (val uiState = state) {
            is UsersUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UsersUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    items(uiState.users) { user ->
                        UserCard(user = user, onUserClick = onUserClick)
                    }
                }
            }

            is UsersUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${uiState.message}")
                }
            }
        }
    }
}

@Composable
fun UserCard(
    user: User,
    onUserClick: (User) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .clickable { onUserClick(user) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            Image(
                painter = rememberAsyncImagePainter(model = user.photo),
                contentDescription = user.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            // User Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = user.company,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}

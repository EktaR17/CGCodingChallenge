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
        topBar = { AppTopBar(title = "Users List") }
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
fun UserCard(user: User, onUserClick: (User) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onUserClick(user) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = rememberAsyncImagePainter(user.photo),
                contentDescription = user.name,
                modifier = Modifier.size(60.dp).padding(end = 12.dp),
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)) {
                Text(text = user.name, fontWeight = FontWeight.Bold)
                Text(text = user.email)
                Text(text = user.company)
            }
        }
    }
}

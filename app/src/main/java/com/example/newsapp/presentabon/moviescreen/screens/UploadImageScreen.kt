package com.example.newsapp.presentabon.moviescreen.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.presentabon.moviescreen.viewmodel.TMDBViewModel
import java.io.File

@Composable
fun UploadImageScreen(viewModel: TMDBViewModel) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val file = uriToFile(context, uri)
            file?.let { viewModel.uploadImage(it) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Select Image")
        }

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.uploadState != null -> {
                Text("Upload Successful: ${uiState.uploadState!!.message}")
            }
            uiState.errorMessage != null -> {
                Text("Error: ${uiState.errorMessage}", color = Color.Red)
            }
            else -> {
                Text("No image uploaded")
            }
        }
    }
}

// Utility function to convert URI to File
fun uriToFile(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    contentResolver.openInputStream(uri)?.use { inputStream ->
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return tempFile
}

package com.example.move_application.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.move_application.data.model.WatchContent

@Composable
fun DetailHeader(content: WatchContent) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = content.posterUrl,
            contentDescription = content.description,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = content.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content.releaseDate ?: "Release Year Not Available",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
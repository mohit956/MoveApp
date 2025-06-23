package com.example.move_application.presentation.details

//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LargeTopAppBar
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarDuration
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.move_application.presentation.details.components.DetailContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailScreen(
    contentId: String,
    contentType: String,
    onNavigateBack: () -> Unit,
    viewModel: DetailsViewModel = koinViewModel()
) {
    val state: DetailsState by viewModel.state.collectAsState()
    val scope: CoroutineScope= rememberCoroutineScope()
    val snackbarHostState : SnackbarHostState = remember { SnackbarHostState() }
    val isMovie: Boolean = contentType == "movie"

    LaunchedEffect(state) {
        (state as? DetailsState.Error)?.let {
            snackbarHostState.showSnackbar(
                message = it.message,
                duration = SnackbarDuration.Long,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(contentId) {viewModel.loadContent(contentId,isMovie) }

    Scaffold(
        topBar = { DeatilsTopBar(state, onNavigateBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedContent (targetState = state,
                transitionSpec = { fadeIn() with fadeOut() }) { currentState ->
                when (currentState) {
                    is DetailsState.Loading -> LoadingIndicator()
                    is DetailsState.Success -> DetailContent(
                        content = currentState.content,
                        modifier = Modifier.fillMaxSize()
                    )
                    is DetailsState.Error -> ErrorScreen(
                        message = currentState.message,
                        onRetry = { scope.launch { viewModel.loadContent(contentId,isMovie) }}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeatilsTopBar(state: DetailsState,onNavigateBack: () -> Unit) {
    LargeTopAppBar(
        title = {
            val title: String = (state as? DetailsState.Success)?.content?.title ?: "Details"
            Text(
                text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.statusBarsPadding()
    )
}

@Composable
    fun LoadingIndicator() {
        val alpha: Float by rememberInfiniteTransition().animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        CircularProgressIndicator(
            modifier = Modifier.size(48.dp).alpha(alpha),
            color = MaterialTheme.colorScheme.primary
        )
    }

@Composable
    fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(24.dp))
        Button (onClick = onRetry) { Text(text = "Retry") }
    }
    }



//
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LargeTopAppBar
//import androidx.compose.material3.LoadingIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarDuration
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//import org.koin.androidx.compose.koinViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailScreen(
//    contentId: String,
//    contentType: String,
//    onNavigateBack: () -> Unit,
//    viewModel: DetailsViewModel = koinViewModel<DetailsViewModel>()
//) {
//    val state : DetailsState by viewModel.state.collectAsState()
//    val scope: CoroutineScope = rememberCoroutineScope()
//    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
//    val isMovie: Boolean = contentType == "movie"
//
//    LaunchedEffect(state) {
//        (state as? DetailsState.Error)?.let {
//            snackbarHostState.showSnackbar(
//                message = it.message,
//                duration = SnackbarDuration.Long,
//                withDismissAction = true
//            )
//        }
//    }
//
//    LaunchedEffect(contentId) {
//        viewModel.loadContent(contentId, isMovie)
//    }
//
//    Scaffold(
//        topBar = { DetailsTopBar(state, onNavigateBack) },
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .background(MaterialTheme.colorScheme.background)
//                .padding(horizontal = 16.dp),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            AnimatedContent (targetState = state,
//                transitionSpec = { fadeIn() with fadeOut() }) { currentState ->
//                when (currentState) {
//                    is DetailsState.Loading -> LoadingIndicator()
//                    is DetailsState.Success -> DetailContent(
//                        content = currentState.content,
//                        modifier = Modifier.fillMaxSize()
//                    )
//                    is DetailsState.Error -> ErrorScreen(
//                        message = currentState.message,
//                        onRetry = { scope.launch { viewModel.loadContent(contentId.isMove) }}
//                    )
//                }
//            }
//        }
//    }
//
//
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun DetailsTopBar(state: DetailsState, onNavigateBack: () -> Unit) {
//        LargeTopAppBar(
//            title = {
//                val title: String = (state as? DetailsState.Success)?.content?.title ?: "Details"
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            },
//            navigationIcon = {
//                IconButton(onClick = onNavigateBack) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//            },
//            colors = TopAppBarDefaults.largeTopAppBarColors(
//                containerColor = MaterialTheme.colorScheme.surface
//            ),
//            modifier = Modifier.statusBarsPadding()
//        )
//    }
//
//
//
//    @Composable
//    fun LoadingIndicator() {
//        val alpha: Float by rememberInfiniteTransition().animateFloat(
//            initialValue = 0.3f,
//            targetValue = 1f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(durationMillis = 1000),
//                repeatMode = RepeatMode.Reverse
//            ), label = ""
//        )
//
//        CircularProgressIndicator(
//            modifier = Modifier.size(48.dp).alpha(alpha),
//            color = MaterialTheme.colorScheme.primary
//        )
//    }
//
//    @Composable
//    fun ErrorScreen(message: String, onRetry: () -> Unit) {
//        Column(
//            modifier = Modifier.fillMaxSize().padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(text = message, color = MaterialTheme.colorScheme.error)
//            Spacer(modifier = Modifier.height(24.dp))
//            Button (onClick = onRetry) { Text(text = "Retry") }
//        }
//    }
//
//
//
//@Composable
//fun DetailsTopBar(x0: DetailsState, x1: () -> Unit) {
//    TODO("Not yet implemented")
//}

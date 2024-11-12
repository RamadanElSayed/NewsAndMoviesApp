package com.example.newsapp.presentabon.newsscreen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.models.Article
import com.example.newsapp.presentabon.component.*
import com.example.newsapp.presentabon.theme.NewsAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    state: NewsScreenState,
    onEvent: (NewsScreenEvent) -> Unit,
    onRedFullStoryButtonClicked: (String) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    NewsAppTheme(darkTheme = isDarkTheme) { // Apply custom theme with only your colors
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 7 })
        val coroutineScope = rememberCoroutineScope()
        val categories = listOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment")
        val sheetState = rememberModalBottomSheetState()
        var shouldButtonSheetShow by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        if (shouldButtonSheetShow) {
            ModalBottomSheet(
                onDismissRequest = { shouldButtonSheetShow = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                content = {
                    state.selectedArticle?.let { article ->
                        BottomSheetContent(
                            article = article,
                            onReedFullStoryButtonClicked = {
                                onRedFullStoryButtonClicked(article.url)
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) shouldButtonSheetShow = false
                                }
                            }
                        )
                    }
                }
            )
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                onEvent(NewsScreenEvent.OnCategoryChanged(category = categories[page]))
            }
        }

        LaunchedEffect(Unit) {
            if (state.searchQuery.isNotEmpty()) {
                onEvent(NewsScreenEvent.OnSearchQueryChanged(searchQuery = state.searchQuery))
            }
        }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                NewsScreenTopBar(
                    scrollBehavior = scrollBehavior,
                    onSearchIconClicked = {
                        coroutineScope.launch {
                            delay(500)
                            focusRequester.requestFocus()
                        }
                        onEvent(NewsScreenEvent.OnSearchIconClicked)
                    },
                    onToggleTheme = onToggleTheme,
                    isDarkTheme = isDarkTheme
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Crossfade(targetState = state.isSearchBarVisible, label = "") { isVisible ->
                    if (isVisible) {
                        Column {
                            SearchAppBar(
                                modifier = Modifier.focusRequester(focusRequester),
                                value = state.searchQuery,
                                onValueChange = { newValue ->
                                    onEvent(NewsScreenEvent.OnSearchQueryChanged(newValue))
                                },
                                onClearClick = {
                                    onEvent(NewsScreenEvent.OnCloseIconClicked)
                                },
                                onSearchClick = {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                            NewsArticlesList(
                                state = state,
                                onCardClicked = { article ->
                                    shouldButtonSheetShow = true
                                    onEvent(NewsScreenEvent.OnNewsCardClicked(article = article))
                                },
                                onRetry = {
                                    onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                                }
                            )
                        }
                    } else {
                        Column {
                            CategortTabRow(
                                pagerState = pagerState,
                                categories = categories,
                                onTabSelected = { index ->
                                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                                }
                            )
                            HorizontalPager(state = pagerState) { page ->
                                NewsArticlesList(
                                    state = state,
                                    onCardClicked = { article ->
                                        shouldButtonSheetShow = true
                                        onEvent(NewsScreenEvent.OnNewsCardClicked(article = article))
                                    },
                                    onRetry = {
                                        onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsArticlesList(
    state: NewsScreenState,
    onCardClicked: (Article) -> Unit,
    onRetry: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.articles) { article ->
            NewsArticleCard(
                article = article,
                onCardClicked = onCardClicked
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        state.error?.let {
            RetryCount(error = it, onRetry = onRetry)
        }
    }
}

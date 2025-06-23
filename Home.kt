package com.example.tasktracker1.ui.theme.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tasktracker1.R
import com.example.tasktracker1.navigation.Screen
import com.example.tasktracker1.ui.theme.composables.BottomModalWithTextField
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // Состояние для темы (false - светлая, true - темная)
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }

    // Установка темы
    val colorScheme = if (isDarkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    // Применяем выбранную цветовую схему
    MaterialTheme(colorScheme = colorScheme) {
        val scope = rememberCoroutineScope()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        var text by remember { mutableStateOf("") }

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { isDarkTheme = !isDarkTheme }
                        ) {
                            Icon(
                                imageVector = if (isDarkTheme) {
                                    Icons.Filled.LightMode
                                } else {
                                    Icons.Filled.DarkMode
                                },
                                contentDescription = "Переключить тему",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    navController.navigate("Vhod")
                                }
                            },
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(text = "Войти")
                        }

                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    navController.navigate("Registration")
                                }
                            },
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(text = "Зарегистрироваться")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { openBottomSheet = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить список")
                }
            }
        ) { paddingValues ->
            if (uiState.todoLists.isNotEmpty()) {
                LazyColumn(contentPadding = paddingValues) {
                    items(uiState.todoLists) { todoList ->
                        Text(
                            text = todoList.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Screen.ListDetailScreen.navigateToListDetailScreen(
                                        id = todoList.id.toString(),
                                        title = todoList.title,
                                        navController = navController
                                    )
                                }
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.create_a_list))
                }
            }
        }

        if (openBottomSheet) {
            BottomModalWithTextField(
                navController = navController,
                title = stringResource(id = R.string.create_a_list),
                value = text,
                placeholderText = stringResource(id = R.string.create_a_list),
                onDismissRequest = { openBottomSheet = false },
                onValueChange = { text = it },
                onSave = {
                    scope.launch {
                        viewModel.createTodoList(text)
                        text = ""
                    }
                }
            )
        }
    }
}
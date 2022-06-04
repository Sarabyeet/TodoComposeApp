package com.example.todomvvm.ui.todo_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todomvvm.ui.theme.CardBorder
import com.example.todomvvm.ui.theme.ListBackground
import com.example.todomvvm.util.UiEvent
import kotlinx.coroutines.flow.collect


@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel(),
) {
    // Retrieve our todos list, but we need it as our compose state, because only that will
    // trigger recompositions
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    // Collect ui events on every screen
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate ->  onNavigate(event)
                else -> Unit
            }
        }
    }


    // For floating action button
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TodoListEvent.OnAddTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a todo")
            }
        },
        topBar = { TopAppBar(title = {
            Text("Tasks")},
            backgroundColor = MaterialTheme.colors.primary) }
    ) {

        if (todos.value.isEmpty()) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(ListBackground)
            ) {
                Text(
                    text = "There are no Tasks 😴",
                    fontSize = 18.sp,
                    color = Color(110, 103, 124, 255),
                    fontFamily = FontFamily.SansSerif,
                    )
            }
        }
        else {

            val context = LocalContext.current
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ListBackground)
            ) {

                items(todos.value) { todo ->
                    TodoItem(
                        todo = todo,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .background(CardBorder)
                            .fillMaxSize()
                            //.clickable{
                            // viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                            //}
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        Toast
                                            .makeText(context,
                                                "Long press to delete the item",
                                                Toast.LENGTH_SHORT)
                                            .show()
                                    },
                                    onLongPress = {
                                        viewModel.onEvent(TodoListEvent.OnDeleteTodoClick(todo))
                                    },
                                    onTap = {
                                        viewModel.onEvent(TodoListEvent.OnTodoClick(todo)) }
                                )
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}



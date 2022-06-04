package com.example.todomvvm.ui.todo_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todomvvm.data.Todo
import com.example.todomvvm.ui.theme.CardBorder
import com.example.todomvvm.ui.theme.DescriptionTextColor
import com.example.todomvvm.ui.theme.ListBackground
import com.example.todomvvm.ui.theme.MainTextColor


@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val paddingModifier = Modifier.padding(start = 15.dp, end = 15.dp,top= 10.dp, bottom = 10.dp)
    Card(
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.dp, CardBorder),
        elevation = 10.dp,
        modifier = paddingModifier,
        contentColor = Color.Black,

    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,

        ){
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = todo.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(0.75f),
                        fontSize = 30.sp,
                        color = MainTextColor,
                        fontWeight = FontWeight.Bold,
                        style= when {
                            todo.isDone -> {
                                TextStyle(textDecoration = TextDecoration.LineThrough)
                            }
                            else -> TextStyle(textDecoration = TextDecoration.None)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        onEvent(TodoListEvent.OnDeleteTodoClick(todo))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete todo",
                            modifier = Modifier.scale(1.2f)
                        )
                    }
                }
                todo.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = DescriptionTextColor,
                        style = when {
                            todo.isDone-> TextStyle(textDecoration = TextDecoration.LineThrough)
                            else -> TextStyle(textDecoration = TextDecoration.None)
                        },
                        modifier = Modifier.padding(end = 24.dp)
                    )
                }
            }
            Checkbox(checked = todo.isDone, onCheckedChange = { isChecked ->
                onEvent(TodoListEvent.OnDoneChange(todo, isChecked))

            }, modifier = Modifier
                .scale(1.75f)
                .padding(8.dp))
        }
    }
}

package com.example.tapviewcompose.view
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MyScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val list1 = (1..100).toList()
    val list2 = (1..100).toList()
    val list3 = (1..100).toList()
    val chunkSize = 10
    var currentScreen by remember { mutableStateOf(1) }
    var colorIndex by remember { mutableStateOf(1) }
    val coroutineScope = rememberCoroutineScope()
    val columnStates = listOf(
        remember { LazyListState() },
        remember { LazyListState() },
        remember { LazyListState() }
    )
    val rowStates = List(10) { remember { LazyListState() } }
    val context = LocalContext.current

    fun scrollToCurrentItem() {
        coroutineScope.launch {
            val currentRow = (colorIndex - 1) / chunkSize
            val itemIndex = (colorIndex - 1) % chunkSize
            columnStates[currentScreen - 1].animateScrollToItem(currentRow)
            rowStates[currentRow].animateScrollToItem(itemIndex)
        }
    }

    LaunchedEffect(colorIndex) {
        scrollToCurrentItem()
    }

    fun moveLeft() {
        if (colorIndex > 1) {
            colorIndex--
        } else {
            when (currentScreen) {
                2, 3 -> {
                    currentScreen--
                    colorIndex = 100
                }
            }
        }
    }

    fun moveRight() {
        if (colorIndex < 100) {
            colorIndex++
        } else {
            when (currentScreen) {
                1, 2 -> {
                    currentScreen++
                    colorIndex = 1
                }
            }
        }
    }

    fun moveUp() {
        if (colorIndex > 10) {
            colorIndex -= 10
        } else {
            when (currentScreen) {
                2, 3 -> {
                    currentScreen--
                    colorIndex += 90
                }
            }
        }
    }

    fun moveDown() {
        if (colorIndex <= 90) {
            colorIndex += 10
        } else {
            when (currentScreen) {
                1, 2 -> {
                    currentScreen++
                    colorIndex -= 90
                }
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getScreenName(screen: Int): String {
        return when (screen) {
            1 -> "Screen A"
            2 -> "Screen B"
            3 -> "Screen C"
            else -> "Unknown Screen"
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Screen A",
                    color = if (currentScreen == 1) {
                        Color.Red
                    } else {
                        Color.Gray
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable {
                            currentScreen = 1
                            colorIndex = 1
                        }
                )
                Text(
                    text = "Screen B",
                    color = if (currentScreen == 2) {
                        Color.Red
                    } else {
                        Color.Gray
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable {
                            currentScreen = 2
                            colorIndex = 1
                        }
                )
                Text(
                    text = "Screen C",
                    color = if (currentScreen == 3) {
                        Color.Red
                    } else {
                        Color.Gray
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable {
                            currentScreen = 3
                            colorIndex = 1
                        }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                LazyColumn(
                    state = columnStates[currentScreen - 1],
                    modifier = Modifier.weight(1f)
                ) {
                    when (currentScreen) {
                        1 -> {
                            item { Text(text = "Screen A", fontWeight = FontWeight.Bold) }
                            items(list1.chunked(chunkSize)) { rowItems ->
                                LazyRow(state = rowStates[list1.chunked(chunkSize).indexOf(rowItems)]) {
                                    items(rowItems) { item ->
                                        Box(
                                            modifier = Modifier
                                                .padding(all = 5.dp)
                                                .border(width = 1.dp, color = Color.Magenta)
                                                .height(50.dp)
                                                .width(80.dp)
                                                .background(
                                                    color = if (item == colorIndex) Color.Yellow else Color.Transparent
                                                )
                                                .clickable {
                                                    showToast("Clicked item $item on Screen A")
                                                }
                                        ) {
                                            Text(
                                                text = item.toString(),
                                                modifier = Modifier
                                                    .padding(all = 5.dp)
                                                    .fillMaxSize(),
                                                style = TextStyle(color = Color.Black),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        2 -> {
                            item { Text(text = "Screen B", fontWeight = FontWeight.Bold) }
                            items(list2.chunked(chunkSize)) { rowItems ->
                                LazyRow(state = rowStates[list2.chunked(chunkSize).indexOf(rowItems)]) {
                                    items(rowItems) { item ->
                                        Box(
                                            modifier = Modifier
                                                .padding(all = 5.dp)
                                                .border(width = 1.dp, color = Color.Magenta)
                                                .height(50.dp)
                                                .width(80.dp)
                                                .background(
                                                    color = if (item == colorIndex) Color.Yellow else Color.Transparent
                                                )
                                                .clickable {
                                                    showToast("Clicked item $item on Screen B")
                                                }
                                        ) {
                                            Text(
                                                text = item.toString(),
                                                modifier = Modifier
                                                    .padding(all = 5.dp)
                                                    .fillMaxSize(),
                                                style = TextStyle(color = Color.Black),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        3 -> {
                            item { Text(text = "Screen C", fontWeight = FontWeight.Bold) }
                            items(list3.chunked(chunkSize)) { rowItems ->
                                LazyRow(state = rowStates[list3.chunked(chunkSize).indexOf(rowItems)]) {
                                    items(rowItems) { item ->
                                        Box(
                                            modifier = Modifier
                                                .padding(all = 5.dp)
                                                .border(width = 1.dp, color = Color.Magenta)
                                                .height(50.dp)
                                                .width(80.dp)
                                                .background(
                                                    color = if (item == colorIndex) Color.Yellow else Color.Transparent
                                                )
                                                .clickable {
                                                    showToast("Clicked item $item on Screen C")
                                                }
                                        ) {
                                            Text(
                                                text = item.toString(),
                                                modifier = Modifier
                                                    .padding(all = 5.dp)
                                                    .fillMaxSize(),
                                                style = TextStyle(color = Color.Black),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { moveUp() }) {
                Text(
                    text = "Top", color = Color.White, modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Blue)
                )
            }

            TextButton(onClick = { moveDown() }) {
                Text(
                    text = "Bottom", color = Color.White, modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Blue)
                )
            }

            TextButton(onClick = { moveLeft() }) {
                Text(
                    text = "Left", color = Color.White, modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Blue)
                )
            }

            TextButton(onClick = { moveRight() }) {
                Text(
                    text = "Right", color = Color.White, modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Blue)
                )
            }

            TextButton(onClick = {
                showToast("Center button clicked on ${getScreenName(currentScreen)} at item $colorIndex")
            }) {
                Text(
                    text = "Center", color = Color.White, modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}


package com.abhi.stageapp.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.abhi.stageapp.states.ApiState
import com.abhi.stageapp.ui.theme.StageAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewState by remember(viewModel) { viewModel.uiState }
                .collectAsState()
            StageAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(
                            text = "Stories",
                            fontSize = TextUnit(
                                32f,
                                TextUnitType.Sp
                            ),
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Start)
                        )


                        Greeting(viewState)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(viewState: ApiState) {
    Column {
        LazyRow {
            items(viewState.account) { account ->
                CircularImageView(
                    imageUrl = account.profileImage,
                    name = account.name,
                    stories = account.stories
                    )

            }
        }
    }


}

@Composable
fun CircularImageView(
    imageUrl: String,
    size: Int = 70,
    name: String,
    stories: List<String>
) {
    var showDialog by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableStateOf(0) }
    val imageCount = stories.size
    val configuration = LocalConfiguration.current
    val progressValues = remember {
        stories.map { mutableStateOf(0f) }
    }
    val progressWidth = ( configuration.screenWidthDp - 8*(imageCount) )/ imageCount


    Column {
        Modifier.padding(horizontal = 20.dp)
        Box(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = {
                    showDialog = true
                    currentIndex = 0
                })
            ,
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(size.dp)
            )


        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = name, fontSize = TextUnit(
                12f,
                TextUnitType.Sp
            ),
            fontFamily = FontFamily.Cursive,
            color = Color.White


        )
    }



    if (showDialog) {
        LaunchedEffect(key1 = currentIndex) {
            while (true) {
                delay(5000)
                if(currentIndex == imageCount - 1)
                    showDialog = false
                currentIndex = (currentIndex + 1) % imageCount
                progressValues.subList(currentIndex, progressValues.size).forEach { it.value = 0f }
            }
        }

        LaunchedEffect(key1 = progressValues) {
            while (true) {
                delay(50)
                progressValues.forEachIndexed { index, progress ->
                    progress.value += 0.01f
                }
            }
        }
        Dialog(onDismissRequest = { showDialog = false }, properties =
            DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(0.dp),
                color = Color.Black
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .width((configuration.screenWidthDp).dp)
                        .height(configuration.screenHeightDp.dp)
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                if(currentIndex == imageCount -1)
                                    showDialog = false
                                currentIndex = (currentIndex + 1) % imageCount
                                progressValues
                                    .subList(currentIndex, progressValues.size)
                                    .forEach { it.value = 0f }

                            }
                    ) {
                        AsyncImage(model = stories[currentIndex],
                            contentDescription = "$name's stories",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            onSuccess = {

                            }
                            )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(18.dp)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            progressValues.forEachIndexed { index, progress ->
                                Box(modifier = Modifier.width(progressWidth.dp - imageCount.dp)) {
                                        LinearProgressIndicator(
                                            progress = {
                                                if(index == currentIndex)
                                                progress.value
                                                else if(index > currentIndex)
                                                    0f
                                                else 100f
                                                       },
                                            modifier = Modifier.fillMaxSize(),
                                            color = Color.White,
                                        )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}




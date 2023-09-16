package com.example.chatclient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatclient.ui.theme.ChatClientTheme
import androidx.compose.ui.text.TextStyle

data class RowData( val text: String, val addSpacer: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(
    chatViewModel : ChatViewModel = viewModel(),
     modifier: Modifier = Modifier
) {

    chatViewModel.connectToWebSocket()
    Column {
        var message by remember {
            mutableStateOf("")
        }
        val rowList = remember { mutableStateListOf<RowData>() }

        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.weight(1f))
            rowList.forEach { rowData ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (rowData.addSpacer) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    val configuration = LocalConfiguration.current
                    val maxWidth = (configuration.screenWidthDp * 3 / 5).dp
                    Text(
                        text = rowData.text,
                        style = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color = Color.Cyan, shape = RoundedCornerShape(10.dp))
                            .widthIn(max = maxWidth)
                            .padding(5.dp)
                    )
                }
            }


        }
        Row {

            TextField(
                value = message,
                onValueChange = {message = it},
                label = { Text("Enter Message") },
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = { rowList.add(RowData( message, true))

                          chatViewModel.sendMessage(message)
                            message = ""},
                modifier = Modifier.padding(16.dp)) {

            }
        }
    }
}

@Composable
fun message(msg:String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween


    ) {

        val configuration = LocalConfiguration.current
        val maxWidth = (configuration.screenWidthDp * 3 / 5).dp
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = msg,
            modifier = Modifier
                .padding(8.dp)
                .background(color = Color.Cyan, shape = RoundedCornerShape(10.dp))
                .widthIn(max = maxWidth)
                .padding(5.dp)
        )
    }
}

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ChatClientTheme {
            Chat()
        }
    }
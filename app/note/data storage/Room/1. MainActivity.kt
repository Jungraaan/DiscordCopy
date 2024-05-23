package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.room.ui.theme.RoomTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
//    val list = db.userDao().getAll()

    Column(modifier = modifier) { // Column으로 변경하여 내용을 정렬합니다.

        var name by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = name, // 현재 입력된 값
            onValueChange = { name = it }, // 값 변경 이벤트 처리
            label = { Text("이름:") }, // 레이블 설정
            modifier = Modifier.fillMaxWidth() // 전체 너비 채움
        )

        var phone by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = phone, // 현재 입력된 값
            onValueChange = { phone = it }, // 값 변경 이벤트 처리
            label = { Text("전화번호:") }, // 레이블 설정
            modifier = Modifier.fillMaxWidth() // 전체 너비 채움
        )

        var email by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = email, // 현재 입력된 값
            onValueChange = { email = it }, // 값 변경 이벤트 처리
            label = { Text("이메일:") }, // 레이블 설정
            modifier = Modifier.fillMaxWidth() // 전체 너비 채움
        )

        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                db.userDao().insertAll(
                    User(
                        userName = name,
                        phoneNumber = phone,
                        email = email,
                    )
                )
            }

        }) {
            Text("등록")

        }
        Column {
            val userList by db.userDao().getAll().collectAsState(initial = emptyList())
            for (user in userList) {
                Text(text = "이름: ${user.userName}")
            }
            userList.forEach {
                Text(text = "이름: ${it.userName}")
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RoomTheme {
        MainScreen()
    }
}

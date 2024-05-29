@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mlkit2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mlkit2.ui.theme.MLKit2Theme
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MLKit2Theme {
                MainScreen()

            }
        }
    }
}

@Composable
fun MainScreen() {
    Column {
        var text by remember { mutableStateOf("") }
        TextField(
            value = text, // "text" -> "value" 변경
            modifier = Modifier
                .fillMaxWidth() // 가로 방향으로 최대 크기 채움
                .padding(16.dp), // 주변 여백 추가
            onValueChange = {
                text = it
            },
        )


        Text(text = text)


        var isDownloaded by remember {
            mutableStateOf(false)
        }


        // Create an English-German translator:
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.KOREAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        val koEnTranslator = remember {
            Translation.getClient(options)
        }

        DownloadModel(koEnTranslator, onSuccess = {
            isDownloaded = true
        })



        var translator by remember {
            mutableStateOf("")
        }


        Button(onClick = {
            koEnTranslator.translate(text)
                .addOnSuccessListener { translatedText ->
                    // Translation successful.
                    translator = translatedText
                }
                .addOnFailureListener { exception ->
                    // Error.
                    // ...
                }
                         }, enabled = isDownloaded) {
            Text(text = "번역하기")
        }
        Text(text = translator)
    }
}

@Composable
fun DownloadModel(koEnTranslator: Translator,
                  onSuccess: () -> Unit,
                  ) {
    LaunchedEffect(key1 = koEnTranslator) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        koEnTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                onSuccess()
            }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MLKit2Theme {
        MainScreen()
    }
}

package com.example.jobmatchai.Screens

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navHostController: NavHostController) {
    var isLoggingOut by remember { mutableStateOf(false) }

    if (isLoggingOut) {
        LogoutLoader(navHostController)
    }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "ATS Checker") },
                    actions = {
                        IconButton(onClick = { isLoggingOut = true }) {
                            Icon(Icons.Filled.Logout, contentDescription = "Logout")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                ATSAnalyzerScreen()
            }
        }
    }


@Composable
fun LogoutLoader(navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(2000) // 4.5 seconds delay
        navHostController.navigate("login")
    }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(color = Color.Blue) // White loader
            Spacer(modifier = Modifier.height(8.dp))
            Text("Logging out...", color = Color.White, style = MaterialTheme.typography.bodyLarge)
        }
    }




@Composable
fun ATSAnalyzerScreen() {
    val context = LocalContext.current
    var jobDescription by remember { mutableStateOf(TextFieldValue("")) }
    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    var atsScore by remember { mutableStateOf<Int?>(null) }
    var isAnalyzing by remember { mutableStateOf(false) }

    val filePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            resumeUri = uri
        }

    LaunchedEffect(isAnalyzing) {
        if (isAnalyzing) {
            delay(2000) // Simulate 2 seconds of analysis
            val resumeText = resumeUri?.let { readTextFromUri(context, it) }
            atsScore = resumeText?.let { analyzeResume(jobDescription.text, it) }
            isAnalyzing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = jobDescription,
            onValueChange = { jobDescription = it },
            label = { Text("Job Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { filePickerLauncher.launch("*/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Resume")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resumeUri != null) {
            val fileName = getFileNameFromUri(context, resumeUri!!)
            Text(text = "Uploaded Resume: $fileName")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (jobDescription.text.isNotEmpty() && resumeUri != null) {
                    isAnalyzing = true
                    atsScore = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = jobDescription.text.isNotEmpty() && resumeUri != null && !isAnalyzing
        ) {
            Text("Analyze Resume")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isAnalyzing) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Analyzing Resume, please wait...")
                }
            }
        }

        if (atsScore != null) {
            Text(
                text = "Your ATS Score: $atsScore/100",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}


@SuppressLint("Recycle")
fun readTextFromUri(context: android.content.Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append("\n")
        }
        reader.close()
        stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@SuppressLint("Range")
fun getFileNameFromUri(context: android.content.Context, uri: Uri): String {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            return it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    }
    return "Unknown File"
}

fun analyzeResume(jobDescription: String, resumeText: String): Int {
    val keywords = extractKeywords(jobDescription)

    val matchedKeywords = keywords.count { keyword ->
        resumeText.contains(keyword, ignoreCase = true)
    }

    return if (keywords.isNotEmpty()) {
        (matchedKeywords * 100 / keywords.size)
    } else {
        0
    }
}

fun extractKeywords(description: String): List<String> {
    val stopWords = setOf(
        "a", "an", "the", "and", "or", "but", "if", "then", "else", "when", "at", "by", "for", "in", "of", "on", "to", "with"
    )

    return description.split(" ")
        .map { it.replace(Regex("[^a-zA-Z0-9]"), "").lowercase() }
        .filter { it.length > 3 && it !in stopWords }
        .distinct()
}

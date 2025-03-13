package com.example.jobmatchai.Screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jobmatchai.Navigation.Routes
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@Composable
fun Profile(navHostController: NavHostController) {
    val context = LocalContext.current
    var jobDescription by rememberSaveable { mutableStateOf("") }
    var resumeFile by remember { mutableStateOf<File?>(null) }
    var atsScore by rememberSaveable { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            resumeFile = context.getFileFromUri(it)
            if (resumeFile == null) {
                errorMessage = "Failed to upload resume. Please try again."
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar with Logout icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { navHostController.navigate(Routes.Login.routes) }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout"
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = "AI Resume & Cover ATS Checker", style = MaterialTheme.typography.headlineSmall)
            }

            item {
                TextField(
                    value = jobDescription,
                    onValueChange = { jobDescription = it },
                    label = { Text("Job Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = { launcher.launch("application/pdf") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Upload Resume")
                }
            }

            if (errorMessage != null) {
                item {
                    Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
                }
            }

            item {
                Button(
                    onClick = {
                        isLoading = true
                        atsScore = calculateATSScore(resumeFile, jobDescription)
                        isLoading = false
                    },
                    enabled = !isLoading && resumeFile != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Generate ATS Score")
                    }
                }
            }

            item {
                Text(text = "ATS Score: $atsScore", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

// Convert URI to File
fun Context.getFileFromUri(uri: Uri): File? {
    return try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "uploaded_resume.pdf")
        inputStream?.use { input ->
            FileOutputStream(file).use { output -> input.copyTo(output) }
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Extract text from PDF using pdfbox-android
fun extractTextFromPdf(file: File): String {
    return try {
        PDDocument.load(file).use { document ->
            PDFTextStripper().getText(document)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

// Calculate ATS Score
fun calculateATSScore(resume: File?, jobDescription: String): Int {
    if (resume == null || jobDescription.isBlank()) return 0
    return try {
        val resumeText = extractTextFromPdf(resume)
        val jobKeywords = jobDescription.split(" ").toSet()
        val matchedKeywords = jobKeywords.count { it in resumeText }
        (matchedKeywords * 100 / jobKeywords.size).coerceIn(0, 100)
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

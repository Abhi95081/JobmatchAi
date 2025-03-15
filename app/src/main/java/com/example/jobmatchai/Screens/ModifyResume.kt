package com.example.jobmatchai.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Modification(navHostController: NavHostController) {
    val context = LocalContext.current
    var jobDescription by remember { mutableStateOf("") }
    var missingKeywords by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Modify Resume for ATS", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Upload Resume Clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Resume")
        }

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            value = jobDescription,
            onValueChange = { jobDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(8.dp)) { innerTextField() }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                missingKeywords = "Leadership, Communication, Teamwork" // Mock response
                Toast.makeText(context, "Analyzing Resume...", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Analyze Resume")
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (missingKeywords.isNotEmpty()) {
            Text("Missing Keywords: $missingKeywords", color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModificationPreview() {
    Modification(navHostController = rememberNavController())
}

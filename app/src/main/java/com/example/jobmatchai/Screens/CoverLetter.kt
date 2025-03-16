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
fun CoverLetter(navHostController: NavHostController) {
    val context = LocalContext.current
    var companyDescription by remember { mutableStateOf("") }
    var coverLetter by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Upload Resume & Generate Cover Letter", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Upload File Clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload File")
        }

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            value = companyDescription,
            onValueChange = { companyDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (companyDescription.isEmpty()) {
                    Text("Enter company description", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (companyDescription.isNotEmpty()) {
                    coverLetter = generateCoverLetter(companyDescription)
                } else {
                    Toast.makeText(context, "Please enter company description", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Cover Letter")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (coverLetter.isNotEmpty()) {
            Text("Generated Cover Letter:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text(coverLetter, modifier = Modifier.background(Color.White).padding(8.dp))
        }
    }
}

fun generateCoverLetter(description: String): String {
    return "Dear Hiring Manager,\n\nI am excited to apply for the opportunity at your company. After learning about your company's $description, I am eager to contribute my skills and experience to your team.\n\nI look forward to the opportunity to discuss how I can be a valuable addition to your organization. Thank you for your time and consideration.\n\nBest regards,\n[Your Name]"
}

@Preview(showBackground = true)
@Composable
fun CoverLetterPreview() {
    CoverLetter(navHostController = rememberNavController())
}

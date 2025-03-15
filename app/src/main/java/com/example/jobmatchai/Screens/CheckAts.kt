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
fun CheckAts(navHostController: NavHostController) {
    val context = LocalContext.current
    var companyDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Upload Resume & Check ATS", style = MaterialTheme.typography.headlineMedium)

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
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Checking ATS...", Toast.LENGTH_SHORT).show()
                // Call backend API to check ATS score
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check ATS")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckAtsPreview() {
    CheckAts(navHostController = rememberNavController())
}

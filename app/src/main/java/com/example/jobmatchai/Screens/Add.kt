package com.example.jobmatchai.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.jobmatchai.Navigation.Routes

@Composable
fun Add(navHostController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Add Pdf", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navHostController.navigate(Routes.CheckAts.routes)
                Toast.makeText(context, "Add File Clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add File")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navHostController.navigate(Routes.Modification.routes)
                Toast.makeText(context, "Check ATS Clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check ATS")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPreview() {
    Add(navHostController = rememberNavController())
}

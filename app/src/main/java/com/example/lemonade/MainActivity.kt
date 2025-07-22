package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("How to make a lemonade? 🍋", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFFC8E6C9)
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color(0xFFFFFFFF),
            tonalElevation = 0.dp
        ) {
            when (currentStep) {
                1 -> LemonStep(
                    textRes   = R.string.lemon_tree,
                    imgRes    = R.drawable.lemon_tree,
                    descRes   = R.string.lemon_tree_content_description,
                    onClick   = {
                        currentStep = 2
                        squeezeCount = (2..4).random()
                    }
                )
                2 -> LemonStep(
                    textRes   = R.string.tapping_lemon,
                    imgRes    = R.drawable.lemon_squeeze,
                    descRes   = R.string.lemon_content_description,
                    onClick   = {
                        squeezeCount--
                        if (squeezeCount <= 0) currentStep = 3
                    }
                )
                3 -> LemonStep(
                    textRes   = R.string.drink_lemonade,
                    imgRes    = R.drawable.lemon_drink,
                    descRes   = R.string.lemonade_content_description,
                    onClick   = { currentStep = 4 }
                )
                else -> LemonStep(
                    textRes   = R.string.tap_glass,
                    imgRes    = R.drawable.lemon_restart,
                    descRes   = R.string.empty_glass_content_description,
                    onClick   = { currentStep = 1 }
                )
            }
        }
    }
}

@Composable
fun LemonStep(
    textRes: Int,
    imgRes: Int,
    descRes: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(32.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC8E6C9)
            ),
            modifier = Modifier.size(250.dp)
        ) {
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = stringResource(id = descRes),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize(0.8f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLemonade() {
    LemonadeTheme {
        LemonadeApp()
    }
}
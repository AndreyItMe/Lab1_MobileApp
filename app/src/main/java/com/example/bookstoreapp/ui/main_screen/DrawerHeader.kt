package com.example.bookstoreapp.ui.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bookstoreapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun DrawerHeader() {
    Column(
        Modifier
            .fillMaxWidth() // Используем fillMaxWidth вместо fillMaxSize, чтобы не растягивать колонку
            .height(170.dp)
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(90.dp),
            painter = painterResource(id = R.drawable.internet_explorer_logo_2005),
            contentDescription = "Internet Explorer Logo" // Добавляем описание для contentDescription
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "BookStore App",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
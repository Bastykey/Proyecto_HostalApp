package com.example.hostalapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.example.hostalapp1.ui.screens.AppRoot
import com.example.hostalapp1.ui.theme.HostalApp1Theme

public val PurpleSoft = Color(0xFF7E57C2)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HostalApp1Theme {
                AppRoot()
            }
        }
    }
}

package io.amuse.codeassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import io.amuse.codeassignment.ui.CatsScreen
import io.amuse.codeassignment.ui.theme.CodeAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeAssignmentTheme {
                Surface {
                    CatsScreen()
                }
            }
        }
    }
}
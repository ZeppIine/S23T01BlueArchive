package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import kr.ac.kumoh.ce.s20180147.s23t01bluearchive.ui.theme.S23T01BlueArchiveTheme

class MainActivity : ComponentActivity() {
    private val viewModel: StudentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: StudentViewModel) {
    val studentList by viewModel.studentList.observeAsState(emptyList())

    S23T01BlueArchiveTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            StudentDrawer(studentList)
        }
    }
}

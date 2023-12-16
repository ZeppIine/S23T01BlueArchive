package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            StudentList(studentList)
        }
    }
}

@Composable
fun StudentItem(student: Student){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextName(student.name)
        TextAcademy(student.academy)
    }
}

@Composable
fun TextAcademy(academy: String){
    Text(academy, fontSize = 20.sp)
}
@Composable
fun TextName(name: String){
    Text(name, fontSize = 30.sp)
}

@Composable
fun StudentList(students: List<Student>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(students) {student ->
            StudentItem(student)
        }
    }
}

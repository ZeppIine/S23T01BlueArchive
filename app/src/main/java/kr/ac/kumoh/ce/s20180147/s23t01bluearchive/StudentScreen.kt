package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

enum class StudentScreen {
    List,
    Detail
}

@Composable
fun StudentApp(studentList: List<Student>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = StudentScreen.List.name,
    ) {
        composable(route = StudentScreen.List.name) {
            StudentList(navController, studentList)
        }
        composable(route = StudentScreen.Detail.name) {
//            StudentDetail(navController, studentList)
        }
    }
}

@Composable
fun StudentList(navController: NavController, students: List<Student>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(students.size) {
            StudentItem(navController, students, it)
        }
    }
}

@Composable
fun StudentItem(navController: NavController, student: List<Student>, id: Int){
    Card(
        modifier = Modifier.clickable {
            navController.navigate(StudentScreen.Detail.name)
        },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "https://picsum.photos/300/300?random=${student[id].id}", // TODO: 이미지 링크 변경 필요
                contentDescription = "${student[id].name} 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 40)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                TextName(student[id].name)
                TextAcademy(student[id].academy)
            }
        }
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


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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun StudentList(students: List<Student>, padding: PaddingValues, onNavTODetail: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = padding
    ) {
        items(students.size) {
            StudentItem(it, students[it], onNavTODetail)
        }
    }
}

@Composable
fun StudentItem(id: Int, student: Student, onNavTODetail: (String) -> Unit){
    Card(
        modifier = Modifier.clickable {
            onNavTODetail(StudentScreen.StudentDetail.name + "/$id")
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
                model = "https://schale.gg/images/student/collection/${student.img}.webp",
                contentDescription = "${student.name} 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(percent = 40)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                TextName(student.name)
                TextAcademy(student.academy)
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

@Composable
fun RatingBar(stars: Int) {
    Row {
        repeat(stars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "성급",
                modifier = Modifier.size(48.dp),
                tint = Color.Yellow)
        }
    }
}

@Composable
fun StudentDetail(student: Student, navController: NavController, padding: PaddingValues) {
    Card(
        modifier = Modifier
            .padding(padding)
            .padding(10.dp)
            .clickable { navController.navigateUp() },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RatingBar(student.star)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                student.name,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                lineHeight = 45.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = "https://schale.gg/images/student/portrait/${student.img}.webp",
                contentDescription = "노래 앨범 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://schale.gg/images/student/collection/${student.img}.webp",
                    contentDescription = "가수 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(student.academy, fontSize = 30.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                student.attack,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )

            Text(
                student.defence,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
    }
}
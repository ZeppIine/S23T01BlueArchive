package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import androidx.compose.foundation.background
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
            Spacer(modifier = Modifier.width(20.dp))

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
                modifier = Modifier.size(40.dp),
                tint = Color.Yellow)
        }
    }
}

@Composable
fun StudentDetail(student: Student, navController: NavController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .clickable { navController.navigateUp() },
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        student.name,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 45.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    RatingBar(student.star)
                }
                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = "https://schale.gg/images/student/portrait/${student.img}.webp",
                    contentDescription = "${student.name} 이미지",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = student.acalogo,
                        contentDescription = "${student.academy} 이미지",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(percent = 20))
                            .background(Color(0xFF40484D))
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(student.academy, fontSize = 30.sp)
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxSize()
                            .padding(8.dp),
                        text = "공격타입",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        text = "방어타입",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxSize()
                            .background(getBackgroundColor(student.attack))
                            .padding(8.dp),
                        text = student.attack,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp,
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxSize()
                            .background(getBackgroundColor(student.defence))
                            .padding(8.dp),
                        text = student.defence,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp,
                        color = Color.White
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .fillMaxSize()
                            .padding(8.dp),
                        text = "무기타입",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        text = "전용무기",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = student.weaponimg,
                        contentDescription = "무기타입 이미지",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(100.dp)
                    )
                    AsyncImage(
                        model = "https://schale.gg/images/weapon/weapon_icon_${student.priweaponimg}.webp",
                        contentDescription = "${student.name} 전용무기 이미지",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(100.dp)
                    )
                }
            }
        }
    }
}

fun getBackgroundColor(type: String): Color {
    return when (type) {
        "폭발", "경장갑" -> Color(0xFFA70C19)
        "관통", "중장갑" -> Color(0xFFB26D1F)
        "신비", "특수장갑" -> Color(0xFF216F9C)
        "진동", "탄력장갑" -> Color(0xFF9431A5)
        else -> Color.Gray // 기본값 또는 다른 처리를 원하는 색상
    }
}
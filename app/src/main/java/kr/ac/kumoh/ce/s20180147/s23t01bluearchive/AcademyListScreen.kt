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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun AcademyList(academies: List<Academy>, padding: PaddingValues, onNavTODetail: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = padding
    ) {
        items(academies.size) {
            AcademyItem(it, academies[it], onNavTODetail)
        }
    }
}

@Composable
fun AcademyItem(id: Int, academy: Academy, onNavTODetail: (String) -> Unit){
    Card(
        modifier = Modifier.clickable {
            onNavTODetail(StudentScreen.AcademyDetail.name + "/$id")
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
                model = "https://picsum.photos/300/300?random=${academy.id}", // TODO: 이미지 링크 변경 필요
                contentDescription = "${academy.club} 이미지",
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
                TextClub(academy.club)
                TextAcademy(academy.academy)
            }
        }
    }
}

@Composable
fun TextClub(club: String){
    Text(club, fontSize = 30.sp)
}
@Composable
fun AcademyDetail(academy: Academy, navController: NavController, padding: PaddingValues) {
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
            Text(
                academy.club,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                lineHeight = 45.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = "https://picsum.photos/300/300?random=${academy.id}",
                contentDescription = "동아리 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://i.pravatar.cc/100?u=${academy.academy}",
                    contentDescription = "학원 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(academy.academy, fontSize = 30.sp)
            }
        }
    }
}
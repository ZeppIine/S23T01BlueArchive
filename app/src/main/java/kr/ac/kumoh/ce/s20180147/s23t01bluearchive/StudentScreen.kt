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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

enum class StudentScreen {
    StudentList,
    AcademyList,
    Detail
}

@Composable
fun StudentDrawer(studentList: List<Student>) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { StudentDrawerSheet(drawerState) },
        gesturesEnabled = true,
    ) {
        Scaffold(
            topBar = {
                StudentTopBar(drawerState)
            },
            bottomBar = {
                StudentBottomNavigation {
                    navController.navigate(it)
                }
            },
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = StudentScreen.StudentList.name
            ) {
                composable(route = StudentScreen.StudentList.name) {
                    StudentList(studentList, innerPadding) {
                        navController.navigate(it)
                    }
                }
                composable(
                    route = StudentScreen.Detail.name + "/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    if(id >= 0)
                        StudentDetail(studentList[id], navController, innerPadding)
                }
                composable(route = StudentScreen.AcademyList.name) {
                    StudentList(studentList, innerPadding) {
                        navController.navigate(it)
                    }
                }
            }
        }
    }
}

@Composable
fun StudentDrawerSheet(drawerState: DrawerState){
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            icon = { StudentIcon() },
            label = { Text("전체 학생 리스트") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
        NavigationDrawerItem(
            icon = { AcademyIcon() },
            label = { Text("전체 학원 리스트") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentTopBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text("블루아카이브 학생명부")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "메뉴"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
fun StudentBottomNavigation(onNavigateToList: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        NavigationBarItem(
            icon = { StudentIcon() },
            label = {
                Text("학생")
            },
            selected = false,
            onClick = { onNavigateToList(StudentScreen.StudentList.name) }
        )
        NavigationBarItem(
            icon = { AcademyIcon() },
            label = {
                Text("학원")
            },
            selected = false,
            onClick = { onNavigateToList(StudentScreen.AcademyList.name) }
        )
    }
}

@Composable
fun StudentIcon() {
    Icon(
        imageVector = Icons.Default.Face,
        contentDescription = "학생"
    )
}

@Composable
fun AcademyIcon() {
    Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "학원"
    )
}

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
            onNavTODetail(StudentScreen.Detail.name + "/$id")
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
                model = "https://picsum.photos/300/300?random=${student.id}", // TODO: 이미지 링크 변경 필요
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
                model = "https://picsum.photos/300/300?random=${student.id}",
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
                    model = "https://i.pravatar.cc/100?u=${student.academy}",
                    contentDescription = "가수 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))

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


//@Composable
//fun StudentApp(studentList: List<Student>) {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = StudentScreen.StudentList.name,
//    ) {
//        composable(route = StudentScreen.StudentList.name) {
//            StudentList(studentList) {
//                navController.navigate(it)
//            }
//        }
//        composable(
//            route = StudentScreen.Detail.name + "/{id}",
//            arguments = listOf(navArgument("id") {
//                type = NavType.IntType
//            })
//        ) {
//            val id = it.arguments?.getInt("id") ?: -1
//            if(id >= 0)
//                StudentDetail(studentList[id], navController)
//        }
//    }
//}
package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch

enum class StudentScreen {
    StudentList,
    AcademyList,
    StudentDetail,
    AcademyDetail
}

@Composable
fun StudentDrawer(studentList: List<Student>, academyList: List<Academy>) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            StudentDrawerSheet(drawerState) {
                navController.navigate(it)
            }
        },
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
                    route = StudentScreen.StudentDetail.name + "/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    if(id >= 0)
                        StudentDetail(studentList[id], navController, innerPadding)
                }
                composable(route = StudentScreen.AcademyList.name) {
                    AcademyList(academyList, innerPadding) {
                        navController.navigate(it)
                    }
                }
                composable(
                    route = StudentScreen.AcademyDetail.name + "/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    if(id >= 0)
                        AcademyDetail(academyList[id], navController, innerPadding)
                }
            }
        }
    }
}

@Composable
fun StudentDrawerSheet(drawerState: DrawerState, onNavToList: (String) -> Unit){
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            icon = { MenuIcon() },
            label = { Text("메뉴") },
            selected = true,
            onClick = { }
        )
        NavigationDrawerItem(
            icon = { StudentIcon() },
            label = { Text("전체 학생 리스트") },
            selected = false,
            onClick = {
                onNavToList(StudentScreen.StudentList.name)
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
                onNavToList(StudentScreen.AcademyList.name)
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
                Text("학원 및 동아리")
            },
            selected = false,
            onClick = { onNavigateToList(StudentScreen.AcademyList.name) }
        )
    }
}

@Composable
fun MenuIcon() {
    Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = "메뉴"
    )
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
package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.dropdownList
import com.geekaid.collagenotes.firebaseDao.screenDao.filterScreenDao
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun FilterScreen(navController: NavHostController, dashboardViewModel: DashboardViewModel) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var course by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var validateInput by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {

            LaunchedEffect(key1 = false) {
                dashboardViewModel.courseList.value =
                    dashboardViewModel.getCourseLists()!!
            }

            if (course.isNotEmpty())
                scope.launch {
                    dashboardViewModel.branchList.value = dashboardViewModel.getBranchList(course)!!
                }

            if (course.isNotEmpty() && branch.isNotEmpty())
                scope.launch {
                    dashboardViewModel.subjectList.value = dashboardViewModel.getSubjectList(course, branch)!!
                }

            course = dropdownList(
                list = dashboardViewModel.courseList.value.list,
                label = "Course",
                defaultValue = dashboardViewModel.filter.value.course,
                validateInput = validateInput
            )

            if (course.isNotEmpty())
                branch = dropdownList(
                    list = dashboardViewModel.branchList.value.list,
                    label = "Branch",
                    defaultValue = dashboardViewModel.filter.value.branch,
                    validateInput = validateInput
                )

            if (course.isNotEmpty() && branch.isNotEmpty())
                subject = dropdownList(
                    list = dashboardViewModel.subjectList.value.list,
                    label = "Subject",
                    defaultValue = dashboardViewModel.filter.value.subject,
                    validateInput = validateInput
                )
        }

        Button(
            onClick = {
                validateInput = true
                scope.launch {
                    if (course.isNotEmpty() && branch.isNotEmpty() && subject.isNotEmpty()) {
                        filterScreenDao(
                            FilterModel(
                                course = course,
                                branch = branch,
                                subject = subject
                            ),
                            context = context,
                            navController = navController
                        )
                    }
                }
            },
            modifier = Modifier.padding(bottom = 64.dp)
        ) {
            Text(text = " Submit ")
        }

    }
}
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
import com.geekaid.collagenotes.model.ListFetch
import com.geekaid.collagenotes.util.csSubjectList
import com.geekaid.collagenotes.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

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
                Timber.i("this launched effect")
                dashboardViewModel.courseList1.value =
                    dashboardViewModel.getCourseLists()?.toObject(ListFetch::class.java)!!
            }

            scope.launch {

                if(course.isNotEmpty()){
                    try {
                        Timber.i("course: $course")
                        dashboardViewModel.branchList12.value =
                            dashboardViewModel.getBranchList("Btech")?.toObject(ListFetch::class.java)!!
                    } catch (e: Exception) {
                        Timber.i("error ${e.message}")
                    }
                }

                Timber.i("course: ${dashboardViewModel.branchList12.value}")
            }
            course = dropdownList(
                list = dashboardViewModel.courseList1.value.list,
                label = "Course",
                defaultValue = dashboardViewModel.filter.value.course,
                validateInput = validateInput
            )

            when (course) {
                "BTech" -> {
                    branch = dropdownList(
                        list = dashboardViewModel.branchList12.value.list,
                        label = "Branch",
                        defaultValue = dashboardViewModel.filter.value.branch,
                        validateInput = validateInput
                    )
                }
            }

            when (branch) {
                "Computer Science" -> subject = dropdownList(
                    list = csSubjectList,
                    label = "Subject",
                    defaultValue = dashboardViewModel.filter.value.subject,
                    validateInput = validateInput
                )

                "Electrical" -> subject = dropdownList(
                    list = csSubjectList,
                    label = "Subject",
                    defaultValue = dashboardViewModel.filter.value.subject,
                    validateInput = validateInput
                )
            }
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
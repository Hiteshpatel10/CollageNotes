package com.geekaid.collagenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekaid.collagenotes.components.dropdownList
import com.geekaid.collagenotes.firebaseDao.screenDao.filterScreenDao
import com.geekaid.collagenotes.model.FilterModel
import com.geekaid.collagenotes.util.*
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

    Timber.i(dashboardViewModel.filter.toString())
    Column(modifier = Modifier.padding(8.dp)) {

        course = dropdownList(
            list = courseList,
            label = "Course",
            defaultValue = dashboardViewModel.filter.value.course
        )

        when (course) {
            "BTech" -> {
                branch = dropdownList(
                    list = branchList,
                    label = "Branch",
                    defaultValue = dashboardViewModel.filter.value.branch
                )
            }
        }

        when (branch) {
            "Computer Science" -> subject = dropdownList(
                list = csSubjectList,
                label = "Subject",
                defaultValue = dashboardViewModel.filter.value.subject
            )
        }

        Button(
            onClick = {
                scope.launch {
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
            },
            modifier = Modifier
                .padding(start = 128.dp, end = 128.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}
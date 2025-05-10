package com.example.flexifit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flexifit.R
import kotlinx.coroutines.launch

@Composable
//navController: NavHostController
fun GymScreen(navController: NavHostController){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabs = listOf("FRONT", "BACK")
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow( selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ){
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page->
            val imageRes = when(page){
                0-> R.drawable.front_body
                1-> R.drawable.back_body
                else -> { R.drawable.front_body }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Tab Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GymScreenPreview(){
//    GymScreen()
}
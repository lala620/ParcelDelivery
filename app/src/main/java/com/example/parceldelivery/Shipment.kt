package com.example.parceldelivery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShipmentScreen(onBackClick: () -> Unit = {}) {
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("In progress", "Pending order", "Cancelled")
    val orange = Color(0xFFFFA500)

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Shipment history",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Image(
                        painter = painterResource(R.drawable.leftchevron),
                        contentDescription = "Back",
                        modifier = Modifier.size(25.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF512DA8)
            ),
            windowInsets = WindowInsets(0)
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFF512DA8),
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 4.dp,
                    color = orange
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            title,
                            color = if (isSelected) orange else Color.White,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(sampleShipments) { shipment ->
                    ShipmentCard(shipment)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ShipmentCard(shipment: Shipment) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusBadge(shipment.status)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Arriving today!", fontWeight = FontWeight.Bold)
                }
                Image(
                    painter = painterResource(id = R.drawable.logistics),
                    contentDescription = "Logistics Icon",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your delivery, #${shipment.trackingId} from ${shipment.origin}, is arriving today!",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${shipment.price} USD",
                color = Color(0xFF5B2EFF),
                fontWeight = FontWeight.Bold
            )
            Text(shipment.date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun StatusBadge(status: ShipmentStatus) {
    val (text, color) = when (status) {
        ShipmentStatus.InProgress -> "in-progress" to Color(0xFF00C48C)
        ShipmentStatus.Pending -> "pending" to Color(0xFFFFC542)
        ShipmentStatus.Loading -> "loading" to Color(0xFF5B2EFF)
    }
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = color, fontSize = 12.sp)
    }
}

// Sample data models
data class Shipment(
    val trackingId: String,
    val origin: String,
    val price: String,
    val date: String,
    val status: ShipmentStatus
)

enum class ShipmentStatus {
    InProgress, Pending, Loading
}

val sampleShipments = listOf(
    Shipment("NEJ20089934122231", "Atlanta", "1400", "Sep 20, 2023", ShipmentStatus.InProgress),
    Shipment("NEJ20089934122231", "Atlanta", "650", "Sep 20, 2023", ShipmentStatus.Pending),
    Shipment("NEJ20089934122231", "Atlanta", "650", "Sep 20, 2023", ShipmentStatus.Pending),
    Shipment("NEJ20089934122231", "Atlanta", "1300", "Sep 20, 2023", ShipmentStatus.Loading)
)

@Preview(showBackground = true)
@Composable
fun ShipmentPreview() {
    MaterialTheme {
        ShipmentScreen()
    }
}

package com.example.parceldelivery



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        TopAppBarSection()

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Tracking",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TrackingCard()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Available vehicles",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        VehicleSection()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSection() {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF512DA8))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.man),
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(32.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Your location",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Wertheimer, Illinois",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Enter the receipt number...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(R.drawable.receipt),
                        contentDescription = "Cart"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(25.dp),
            singleLine = true
        )
    }
}



@Composable
fun TrackingCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.5.dp),
        colors =  CardDefaults.cardColors(containerColor =Color(0xFFFAF9F6) )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Shipment Number Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Shipment Number", fontWeight = FontWeight.Normal)
                    Text("NEJ20089934122231", fontWeight = FontWeight.ExtraBold)
                }
                Image(
                    painter = painterResource(R.drawable.tracking),
                    contentDescription = "Tracking Icon",
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sender & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.birthdayboy),
                        contentDescription = "Sender Icon",
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Sender", fontWeight = FontWeight.Light)
                        Text("Atlanta, 5243")
                    }
                }

                Column( verticalArrangement = Arrangement.Center) {
                    Text("Time", fontWeight = FontWeight.Light)
                    Text("2 day - 3 days", color = Color.Green)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Receiver & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.birthdayboy),
                        contentDescription = "Receiver Icon",
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Receiver", fontWeight = FontWeight.Light)
                        Text("Chicago, 6342")
                    }
                }

                Column( verticalArrangement = Arrangement.Center) {
                    Text("Status", fontWeight = FontWeight.Light)
                    Text("Waiting to collect")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(
                color = Color.LightGray,
                thickness = 1.5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (16).dp)

            )


            TextButton(
                onClick = { /* Handle add stop */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("+ Add Stop", color = Color(0xFFFF8C00))
            }
        }
    }
}




@Composable
fun VehicleSection() {
    val vehicles = listOf(
        Triple("Ocean freight", "International", R.drawable.cargoship),
        Triple("Cargo freight", "Reliable", R.drawable.cargotruck),
        Triple("Air freight", "International", R.drawable.transport)
    )
    val offWhite = Color(0xFFFAF9F6)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(vehicles) { (title, subtitle, iconRes) ->
            Card(
                modifier = Modifier
                    .size(width = 140.dp, height = 160.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = offWhite)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Image at the bottom, partially visible
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 5.dp, y = 5.dp) // Position & partial cutoff
                            .size(100.dp) // Large enough to be partially out of bounds
                            .clip(RectangleShape) // Optional: clip to prevent overflow
                    )


                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(title, fontWeight = FontWeight.Bold)
                        Text(subtitle, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}





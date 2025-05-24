package com.example.parceldelivery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculate(onBackClick: () -> Unit = {}) {
    val packagingOptions = listOf("Box", "Envelope", "Crate")
    var selectedPackaging by remember { mutableStateOf(packagingOptions[0]) }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("Documents", "Glass", "Liquid", "Food", "Electronic", "Product", "Others")
    val selectedCategories = remember { mutableStateListOf<String>() }

    var senderLocation by remember { mutableStateOf("") }
    var receiverLocation by remember { mutableStateOf("") }
    var approxWeight by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Calculate",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Destination", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CustomInputField(
                        value = senderLocation,
                        onValueChange = { senderLocation = it },
                        placeholder = "Sender location",
                        leadingIcon = R.drawable.geographical
                    )

                    Spacer(Modifier.height(12.dp))

                    CustomInputField(
                        value = receiverLocation,
                        onValueChange = { receiverLocation = it },
                        placeholder = "Receiver location",
                        leadingIcon = R.drawable.geographical
                    )

                    Spacer(Modifier.height(12.dp))

                    CustomInputField(
                        value = approxWeight,
                        onValueChange = { approxWeight = it.filter { ch -> ch.isDigit() } },
                        placeholder = "Approx weight",
                        leadingIcon = R.drawable.scale,
                        keyboardType = KeyboardType.Number
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Packaging", fontWeight = FontWeight.Bold)
            Text("What are you sending?", style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = selectedPackaging,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.logistics),
                            contentDescription = "Packaging icon",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    packagingOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedPackaging = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Categories", fontWeight = FontWeight.Bold)
            Text("What are you sending?", style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategories.contains(category)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedCategories.remove(category)
                            } else {
                                selectedCategories.add(category)
                            }
                        },
                        label = { Text(category) },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    // Handle calculate button click here
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
            ) {
                Text(text = "Calculate")
            }

            Spacer(Modifier.height(16.dp)) // Extra padding at bottom
        }
    }
}

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Int,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFEFEFEF), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = leadingIcon),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(Color.Black),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(placeholder, color = Color.Gray)
                }
                innerTextField()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatePreview() {
    MaterialTheme {
        Calculate()
    }
}

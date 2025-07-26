package com.example.cottonco.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cottonco.repository.ProductRepositoryImpl
import com.example.cottonco.viewModel.ProductViewModel

class UpdateProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpdateProductBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductBody() {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    val productId: String? = activity?.intent?.getStringExtra("productId")
    val products = viewModel.products.observeAsState(initial = null)

    // Fetch product data when component is first launched
    LaunchedEffect(productId) {
        if (!productId.isNullOrEmpty()) {
            println("Fetching product with ID: $productId")
            viewModel.getProductById(productId)
        } else {
            println("Product ID is null or empty")
            isLoading = false
        }
    }

    // Update state when product data is received
    LaunchedEffect(products.value) {
        products.value?.let { product ->
            println("Product data received: ${product.productName}")
            name = product.productName
            price = product.price.toString()
            description = product.description
            imageUrl = product.image
            isLoading = false
        }
    }

    // Gradient background matching the app theme
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFADA), // Main cream color
            Color(0xFFFFF8E1), // Lighter cream
            Color(0xFFFFFEF7)  // Very light cream
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Update Product",
                        color = Color(0xFF8B4513),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFADA)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = "Update Product Details",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color(0xFF8B4513),
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Loading product details...",
                            color = Color(0xFF8B4513),
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        // Product Image
                        if (imageUrl.isNotEmpty()) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Product Name Field
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Product Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Price Field
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Price") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Description Field
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Update Button
                        Button(
                            onClick = {
                                if (name.isNotEmpty() && price.isNotEmpty() && description.isNotEmpty()) {
                                    val data = mutableMapOf<String, Any?>()
                                    data["productName"] = name
                                    data["price"] = price.toDoubleOrNull() ?: 0.0
                                    data["description"] = description
                                    data["image"] = imageUrl

                                    viewModel.updateProduct(productId.toString(), data) { success, message ->
                                        if (success) {
                                            Toast.makeText(context, "Product updated successfully!", Toast.LENGTH_SHORT).show()
                                            activity?.finish()
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B4513)
                            )
                        ) {
                            Text(
                                text = "Update Product",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUpdateProductBody() {
    UpdateProductBody()
}

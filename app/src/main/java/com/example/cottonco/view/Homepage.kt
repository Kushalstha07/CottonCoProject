package com.example.cottonco.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.PaddingValues
import coil.compose.AsyncImage
import com.example.cottonco.repository.ProductRepositoryImpl
import com.example.cottonco.viewModel.ProductViewModel
import com.example.cottonco.view.AddProductActivity
import com.example.cottonco.view.UpdateProductActivity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.widthIn
import com.example.cottonco.view.LoginActivity

class Homepage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomepageBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageBody() {

    data class BottomNavItem(val label: String, val icon: ImageVector)

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Search", Icons.Default.Search),
        BottomNavItem("Profile", Icons.Default.Person)
    )

    var selectedIndex by remember { mutableStateOf(0) }

    val context = LocalContext.current
    // Cream gradient background
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
                        "CottonCo",
                        color = Color(0xFF8B4513),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label, tint = Color(0xFF8B4513)) },
                        label = { Text(item.label, color = Color(0xFF8B4513)) },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFFFFADA),
                            selectedIconColor = Color(0xFF8B4513),
                            selectedTextColor = Color(0xFF8B4513),
                            unselectedIconColor = Color(0xFFD2691E),
                            unselectedTextColor = Color(0xFFD2691E)
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedIndex == 0) { // Only show FAB on Home page
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(context, AddProductActivity::class.java)
                        context.startActivity(intent)
                    },
                    containerColor = Color(0xFF8B4513),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(brush = gradientBrush)
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)), // Removed .padding(16.dp) here
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // Keep padding for content only
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (selectedIndex) {
                        0 -> Home1()
                        1 -> Home2()
                        2 -> Home3()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevNavigation() {
    HomepageBody()
}

@Composable
fun Home1() {
    val context = LocalContext.current
    val activity = context as? Activity
    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }
    val products = viewModel.allProducts.observeAsState(initial = emptyList())
    val loading = viewModel.loading.observeAsState(initial = true)
    LaunchedEffect(Unit) { viewModel.getAllProducts() }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
        ) {
                if (loading.value) {
                    item { CircularProgressIndicator(color = Color(0xFF8B4513)) }
                } else {
                    items(products.value.size) { index ->
                        val eachProduct = products.value[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFADA)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                // Product Image
                                if (!eachProduct?.image.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = eachProduct?.image,
                                        contentDescription = "Product Image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                
                                Text(
                                    text = eachProduct?.productName ?: "",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF8B4513),
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "Price: $${eachProduct?.price}",
                                    color = Color(0xFFD2691E),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = eachProduct?.description ?: "",
                                    color = Color(0xFF654321),
                                    fontSize = 13.sp
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(context, UpdateProductActivity::class.java)
                                            intent.putExtra("productId", "${eachProduct?.productId}")
                                            context.startActivity(intent)
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = Color(0xFFD2691E)
                                        )
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = null)
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.deleteProduct(eachProduct?.productId.toString()) { success, message ->
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = Color.Red
                                        )
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = null)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



@Composable
fun Home2() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var isSearchExpanded by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val activity = context as? Activity
    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }
    val allProducts = viewModel.allProducts.observeAsState(initial = emptyList())
    val loading = viewModel.loading.observeAsState(initial = true)
    
    // Filter options
    val filterOptions = listOf("All", "Name", "Price", "Description")
    
    LaunchedEffect(Unit) { 
        viewModel.getAllProducts() 
    }
    
    // Filter products based on search query and selected filter
    val filteredProducts = remember(allProducts.value, searchQuery, selectedFilter) {
        if (searchQuery.isEmpty()) {
            allProducts.value
        } else {
            allProducts.value.filter { product ->
                when (selectedFilter) {
                    "Name" -> product?.productName?.contains(searchQuery, ignoreCase = true) == true
                    "Price" -> product?.price?.toString()?.contains(searchQuery, ignoreCase = true) == true
                    "Description" -> product?.description?.contains(searchQuery, ignoreCase = true) == true
                    else -> {
                        product?.productName?.contains(searchQuery, ignoreCase = true) == true ||
                        product?.price?.toString()?.contains(searchQuery, ignoreCase = true) == true ||
                        product?.description?.contains(searchQuery, ignoreCase = true) == true
                    }
                }
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        // Search Header
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 340.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFADA)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Search Title
                Text(
                    text = "Search Products",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search products...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8B4513),
                        unfocusedBorderColor = Color(0xFFD2691E),
                        focusedLabelColor = Color(0xFF8B4513),
                        unfocusedLabelColor = Color(0xFFD2691E)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Filter Dropdown
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filter by:",
                        color = Color(0xFF8B4513),
                        fontWeight = FontWeight.Medium
                    )
                    
                    Box {
                        OutlinedTextField(
                            value = selectedFilter,
                            onValueChange = { },
                            readOnly = true,
                            modifier = Modifier.width(120.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Show filters",
                                    tint = Color(0xFF8B4513),
                                    modifier = Modifier.clickable { isSearchExpanded = !isSearchExpanded }
                                )
                            }
                        )
                        
                        DropdownMenu(
                            expanded = isSearchExpanded,
                            onDismissRequest = { isSearchExpanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            filterOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedFilter = option
                                        isSearchExpanded = false
                                    },
                                    modifier = Modifier.background(
                                        if (selectedFilter == option) Color(0xFFFFFADA) else Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
                
                // Search Results Count
                if (searchQuery.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${filteredProducts.size} result${if (filteredProducts.size != 1) "s" else ""} found",
                        color = Color(0xFFD2691E),
                        fontSize = 12.sp
                    )
                }
            }
        }
        
        // Products List
        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF8B4513))
            }
        } else if (filteredProducts.isEmpty() && searchQuery.isNotEmpty()) {
            // No results found
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color(0xFFD2691E)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No products found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8B4513)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try adjusting your search terms",
                        fontSize = 14.sp,
                        color = Color(0xFFD2691E)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(filteredProducts.size) { index ->
                    val eachProduct = filteredProducts[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFADA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            // Product Image
                            if (!eachProduct?.image.isNullOrEmpty()) {
                                AsyncImage(
                                    model = eachProduct?.image,
                                    contentDescription = "Product Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            
                            Text(
                                text = eachProduct?.productName ?: "",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8B4513),
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Price: $${eachProduct?.price}",
                                color = Color(0xFFD2691E),
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = eachProduct?.description ?: "",
                                color = Color(0xFF654321),
                                fontSize = 13.sp
                            )
                            
                            // Action Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = {
                                        val intent = Intent(context, UpdateProductActivity::class.java)
                                        intent.putExtra("productId", "${eachProduct?.productId}")
                                        context.startActivity(intent)
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = Color(0xFFD2691E)
                                    )
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                }
                                IconButton(
                                    onClick = {
                                        viewModel.deleteProduct(eachProduct?.productId.toString()) { success, message ->
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        }
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = Color.Red
                                    )
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Home3() {
    val context = LocalContext.current
    val activity = context as? Activity
    val repo = remember { com.example.cottonco.repository.UserRepositoryImpl() }
    val userViewModel = remember { com.example.cottonco.viewModel.UserViewModel(repo) }
    val firebaseUser = userViewModel.getCurrentUser()
    val userLive = userViewModel.users.observeAsState(initial = null)
    var isLoading by remember { mutableStateOf(true) }

    // Fetch user info on launch
    LaunchedEffect(firebaseUser) {
        firebaseUser?.uid?.let { userViewModel.getUserById(it) }
    }
    // Set loading false when userLive updates
    LaunchedEffect(userLive.value) {
        if (userLive.value != null) isLoading = false
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFADA),
            Color(0xFFFFF8E1),
            Color(0xFFFFFEF7)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 340.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            if (isLoading) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color(0xFF8B4513))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading profile...", color = Color(0xFF8B4513))
                }
            } else {
                val user = userLive.value
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(Color(0xFF8B4513)),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials =
                            (user?.firstName?.firstOrNull()?.uppercaseChar()?.toString() ?: "") +
                            (user?.lastName?.firstOrNull()?.uppercaseChar()?.toString() ?: "")
                        Text(
                            text = initials,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Name
                    Text(
                        text = "${user?.firstName ?: ""} ${user?.lastName ?: ""}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFF8B4513)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // Email
                    Text(
                        text = user?.email ?: "",
                        color = Color(0xFFD2691E),
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Gender & Address
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Gender:",
                            color = Color(0xFF8B4513),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = user?.gender ?: "-",
                            color = Color(0xFFD2691E)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Address:",
                            color = Color(0xFF8B4513),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = user?.address ?: "-",
                            color = Color(0xFFD2691E),
                            modifier = Modifier.widthIn(max = 160.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { /* TODO: Edit profile */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD2691E)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Edit Profile", color = Color.White)
                        }
                        Button(
                            onClick = {
                                userViewModel.logout { success, _ ->
                                    if (success) {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        context.startActivity(intent)
                                        activity?.finish()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B4513)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Logout", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
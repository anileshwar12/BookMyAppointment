package com.acdevs.bookmyappointment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AirlineSeatLegroomExtra
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Hearing
import androidx.compose.material.icons.outlined.MedicalInformation
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.acdevs.bookmyappointment.R
import com.acdevs.bookmyappointment.ui.components.CategoryCard
import com.acdevs.bookmyappointment.ui.components.TopDoctorsSection
import com.acdevs.bookmyappointment.viewmodel.AppointmentViewModel

@Composable
fun DoctorAppointmentApp(navController: NavHostController) {
    val viewModel: AppointmentViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("slotSelection/{doctorName}") { backStackEntry ->
            val doctorName = backStackEntry.arguments?.getString("doctorName") ?: "Unknown"
            BookingScreen(
                viewModel = viewModel,
                doctorName = doctorName,
                onBookingComplete = {
                    navController.navigate("appointments") {
                        popUpTo("home")
                    }
                }
            )
        }
        composable("appointments") {
            AppointmentsScreen(viewModel, navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar()
            //LocationDropdown()
            BannerSection()
            TopCategoriesSection()
            TopDoctorsSection(navController)
        }
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
    TextField(
        value = query,
        onValueChange = { query = it },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        placeholder = { Text("Search for doctors, specialties...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun LocationDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("Select Location") }
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = selectedLocation,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(12.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Hyderabad") }, onClick = { selectedLocation = "Hyderabad"; expanded = false })
            DropdownMenuItem(text = { Text("Bangalore") }, onClick = { selectedLocation = "Bangalore"; expanded = false })
        }
    }
}

@Composable
fun BannerSection() {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.banner2),
            contentDescription = "Promotional Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
    }
}



@Composable
fun TopCategoriesSection() {
    val categories = listOf(
        "General Physician" to Icons.Outlined.MedicalServices,
        "ENT Specialist" to Icons.Outlined.Hearing,
        "Cardiologist" to Icons.Outlined.Favorite,
        "Pediatrician" to Icons.Outlined.ChildCare,
        "Neurologist" to Icons.Outlined.Psychology,
        "Orthopedic" to Icons.Outlined.AirlineSeatLegroomExtra,
        "Dental Care" to Icons.Outlined.MedicalInformation

    )


    Column(modifier = Modifier.padding(16.dp)) {
        Text("Top Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(categories) { (category, icon) ->
                CategoryCard(category, icon)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "AI Bot") },
            label = { Text("AI Bot") },
            selected = false,
            onClick = { navController.navigate("ai_bot") }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Book, contentDescription = "Appointments") },
            label = { Text("Appointments") },
            selected = false,
            onClick = { navController.navigate("appointments") }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("profile") }
        )
    }
}


package com.example.cottonco.model

data class UserModel(
    // User data model for Firebase authentication
    val userId: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var address: String = "",
    var selectedOptionText: String = "" // Changed from Any? to String with default value
)



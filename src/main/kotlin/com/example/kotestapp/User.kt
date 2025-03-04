package com.example.kotestapp

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val age: Int
) {
    init {
        require(id > 0) { "User ID must be positive" }
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.contains("@")) { "Invalid email format" }
        require(age >= 0) { "Age cannot be negative" }
    }
    
    fun isAdult(): Boolean = age >= 18
    
    fun getEmailDomain(): String = email.substringAfter('@')
}

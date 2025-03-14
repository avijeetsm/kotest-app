package com.example.kotestapp

class Calculator {
    fun add(a: Int, b: Int): Int = a + b
    
    fun subtract(a: Int, b: Int): Int = a - b
    
    fun multiply(a: Int, b: Int): Int = a * b
    
    fun divide(one: Int, two: Int): Int {
        require(two != 0) { "Cannot divide by zero" }
        return one / two
    }
    
    fun factorial(n: Int): Long {
        require(n >= 0) { "Factorial is not defined for negative numbers" }
        return when (n) {
            0, 1 -> 1
            else -> (2..n).fold(1L) { acc, i -> acc * i }
        }
    }
}

package com.example.kotestapp

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.data.forAll
import io.kotest.data.row

class CalculatorTest : FunSpec({

    val calculator = Calculator()
    
    test("Addition should work correctly") {
        calculator.add(2, 3) shouldBe 5
        calculator.add(-1, 1) shouldBe 0
        calculator.add(0, 0) shouldBe 0
    }
    
    test("Subtraction should work correctly") {
        calculator.subtract(5, 3) shouldBe 2
        calculator.subtract(1, 5) shouldBe -4
        calculator.subtract(0, 0) shouldBe 0
    }
    
    test("Multiplication should work correctly") {
        calculator.multiply(2, 3) shouldBe 6
        calculator.multiply(0, 5) shouldBe 0
        calculator.multiply(-2, 3) shouldBe -6
    }
    
    test("Division should work correctly") {
        calculator.divide(6, 3) shouldBe 2
        calculator.divide(7, 2) shouldBe 3  // Integer division
        calculator.divide(-6, 3) shouldBe -2
    }
    
    test("Division by zero should throw exception") {
        shouldThrow<IllegalArgumentException> {
            calculator.divide(5, 0)
        }
    }
    
    test("Factorial should return correct values") {
        calculator.factorial(0) shouldBe 1
        calculator.factorial(1) shouldBe 1
        calculator.factorial(5) shouldBe 120
    }
    
    test("Factorial of negative number should throw exception") {
        shouldThrow<IllegalArgumentException> {
            calculator.factorial(-1)
        }
    }
    
    test("Addition property test using data") {
        forAll(
            row(1, 2, 3),
            row(0, 0, 0),
            row(-1, 1, 0),
            row(5, -5, 0)
        ) { a, b, expected ->
            calculator.add(a, b) shouldBe expected
        }
    }
})

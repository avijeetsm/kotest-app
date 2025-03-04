package com.example.kotestapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.property.checkAll
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string

class StringUtilsTest : DescribeSpec({
    
    describe("StringUtils") {
        
        describe("isPalindrome") {
            it("should correctly identify palindromes") {
                StringUtils.isPalindrome("racecar") shouldBe true
                StringUtils.isPalindrome("A man a plan a canal Panama") shouldBe true
                StringUtils.isPalindrome("hello") shouldBe false
            }
            
            it("should ignore case and non-alphanumeric characters") {
                StringUtils.isPalindrome("Race Car!") shouldBe true
                StringUtils.isPalindrome("10,01") shouldBe true
                StringUtils.isPalindrome("A Santa at NASA") shouldBe true
            }
            
            context("property-based testing") {
                it("a string equals its reverse for palindromes") {
                    checkAll<String> { s ->
                        val palindrome = s + s.reversed()
                        StringUtils.isPalindrome(palindrome) shouldBe true
                    }
                }
                
                it("empty string is a palindrome") {
                    StringUtils.isPalindrome("") shouldBe true
                }
                
                it("single character is a palindrome") {
                    StringUtils.isPalindrome("a") shouldBe true
                    StringUtils.isPalindrome("5") shouldBe true
                    StringUtils.isPalindrome("!") shouldBe true
                }
            }
        }
        
        describe("countOccurrences") {
            it("should count occurrences correctly") {
                StringUtils.countOccurrences("banana", "a") shouldBe 3
                StringUtils.countOccurrences("hello world", "l") shouldBe 3
                StringUtils.countOccurrences("hello", "x") shouldBe 0
            }
            
            it("should handle empty strings") {
                StringUtils.countOccurrences("", "a") shouldBe 0
                StringUtils.countOccurrences("hello", "") shouldBe 0
                StringUtils.countOccurrences("", "") shouldBe 0
            }
            
            it("should count non-overlapping occurrences correctly") {
                StringUtils.countOccurrences("aaa", "aa") shouldBe 1
                StringUtils.countOccurrences("aaaa", "aa") shouldBe 2
                StringUtils.countOccurrences("ababababa", "aba") shouldBe 2
            }
        }
        
        describe("truncate") {
            it("should truncate strings longer than maxLength") {
                StringUtils.truncate("Hello, world!", 5) shouldBe "Hello..."
                StringUtils.truncate("Testing", 4) shouldBe "Test..."
            }
            
            it("should not truncate strings shorter than or equal to maxLength") {
                StringUtils.truncate("Hello", 5) shouldBe "Hello"
                StringUtils.truncate("Hello", 10) shouldBe "Hello"
                StringUtils.truncate("", 5) shouldBe ""
            }
            
            context("property-based testing") {
                it("result should never be longer than maxLength + ellipsis") {
                    checkAll(Arb.string(), Arb.int(0, 50)) { s, maxLength ->
                        val result = StringUtils.truncate(s, maxLength)
                        if (s.length <= maxLength) {
                            result shouldBe s
                        } else {
                            result.length shouldBe maxLength + 3 // +3 for "..."
                            result shouldContain "..."
                        }
                    }
                }
                
                it("truncated string should start with original string prefix") {
                    checkAll(Arb.string(minSize = 10), Arb.int(1, 9)) { s, maxLength ->
                        val result = StringUtils.truncate(s, maxLength)
                        result.startsWith(s.take(maxLength)) shouldBe true
                    }
                }
            }
        }
    }
})

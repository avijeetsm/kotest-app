package com.example.kotestapp

object StringUtils {
    fun isPalindrome(text: String): Boolean {
        val cleaned = text.lowercase().replace(Regex("[^a-z0-9]"), "")
        return cleaned == cleaned.reversed()
    }
    
    fun countOccurrences(text: String, substring: String): Int {
        if (substring.isEmpty() || text.isEmpty()) return 0
        
        var count = 0
        var index = 0
        
        while (index != -1) {
            index = text.indexOf(substring, index)
            if (index != -1) {
                count++
                index += substring.length
            }
        }
        
        return count
    }
    
    fun truncate(text: String, maxLength: Int): String {
        if (text.length <= maxLength) return text
        return text.take(maxLength) + "..."
    }
}

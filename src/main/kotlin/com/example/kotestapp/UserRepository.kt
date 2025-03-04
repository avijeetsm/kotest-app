package com.example.kotestapp

class UserRepository {
    private val users = mutableMapOf<Int, User>()
    
    fun addUser(user: User): Boolean {
        if (users.containsKey(user.id)) return false
        users[user.id] = user
        return true
    }
    
    fun getUser(id: Int): User? = users[id]
    
    fun updateUser(user: User): Boolean {
        if (!users.containsKey(user.id)) return false
        users[user.id] = user
        return true
    }
    
    fun deleteUser(id: Int): Boolean {
        return users.remove(id) != null
    }
    
    fun getAllUsers(): List<User> = users.values.toList()
    
    fun findUsers(predicate: (User) -> Boolean): List<User> =
        users.values.filter(predicate)
    
    fun count(): Int = users.size
    
    fun clear() = users.clear()
}

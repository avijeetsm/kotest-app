package com.example.kotestapp

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize

class UserRepositoryTest : BehaviorSpec({
    
    given("An empty user repository") {
        val repository = UserRepository()
        
        `when`("a new user is added") {
            val user = User(1, "John Doe", "john@example.com", 30)
            val result = repository.addUser(user)
            
            then("the repository should return true") {
                result shouldBe true
            }
            
            then("the user count should be 1") {
                repository.count() shouldBe 1
            }
            
            then("the user should be retrievable by ID") {
                repository.getUser(1) shouldBe user
            }
        }
        
        `when`("multiple users are added") {
            val user1 = User(1, "John Doe", "john@example.com", 30)
            val user2 = User(2, "Jane Smith", "jane@example.com", 25)
            repository.addUser(user1)
            repository.addUser(user2)
            
            then("the repository should contain all users") {
                repository.count() shouldBe 2
                repository.getAllUsers() shouldContain user1
                repository.getAllUsers() shouldContain user2
            }
            
            `when`("filtering adult users") {
                val adults = repository.findUsers { it.isAdult() }
                
                then("it should return all users who are 18 or older") {
                    adults shouldHaveSize 2
                    adults shouldContain user1
                    adults shouldContain user2
                }
            }
            
            `when`("a user is updated") {
                val updatedUser = User(1, "John Updated", "john@example.com", 31)
                val result = repository.updateUser(updatedUser)
                
                then("the update should be successful") {
                    result shouldBe true
                }
                
                then("the user should reflect the changes") {
                    repository.getUser(1) shouldBe updatedUser
                }
            }
            
            `when`("a user is deleted") {
                val result = repository.deleteUser(1)
                
                then("the delete should be successful") {
                    result shouldBe true
                }
                
                then("the user should no longer exist") {
                    repository.getUser(1) shouldBe null
                }
                
                then("the user count should decrease") {
                    repository.count() shouldBe 1
                }
            }
        }
        
        `when`("the repository is cleared") {
            repository.addUser(User(1, "John Doe", "john@example.com", 30))
            repository.addUser(User(2, "Jane Smith", "jane@example.com", 25))
            repository.clear()
            
            then("it should be empty") {
                repository.count() shouldBe 0
                repository.getAllUsers() shouldBe emptyList()
            }
        }
    }
    
    given("A repository with existing users") {
        val repository = UserRepository()
        val existingUser = User(1, "Existing User", "existing@example.com", 40)
        repository.addUser(existingUser)
        
        `when`("adding a user with an existing ID") {
            val duplicateUser = User(1, "Duplicate User", "duplicate@example.com", 25)
            val result = repository.addUser(duplicateUser)
            
            then("it should fail") {
                result shouldBe false
            }
            
            then("the original user should remain unchanged") {
                repository.getUser(1) shouldBe existingUser
            }
        }
        
        `when`("updating a non-existent user") {
            val nonExistentUser = User(999, "Non-existent", "non@example.com", 50)
            val result = repository.updateUser(nonExistentUser)
            
            then("it should fail") {
                result shouldBe false
            }
        }
        
        `when`("deleting a non-existent user") {
            val result = repository.deleteUser(999)
            
            then("it should fail") {
                result shouldBe false
            }
        }
    }
})

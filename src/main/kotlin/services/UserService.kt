package ru.demyanovaf.kotlin.services

import org.springframework.stereotype.Service
import ru.demyanovaf.kotlin.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class UserService {

    private val createdUsers = mutableMapOf<String, User>()

    @OptIn(ExperimentalUuidApi::class)
    fun createUser(login: String): User {
        return createdUsers[login] ?: User(login = login).apply { createdUsers.putIfAbsent(login, this) }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun findById(id: Uuid): User? {
        val user = createdUsers.values.find { it.id == id }
        user ?: println("User not found with id $id")
        return user
    }

    fun showAll(): List<User> {
        return createdUsers.values.toList()
    }
}
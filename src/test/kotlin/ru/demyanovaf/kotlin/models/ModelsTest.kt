package ru.demyanovaf.kotlin.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ModelsTest {

    @Test
    fun testUserCreation() {
        val user = User(login = "testLogin")
        assertNotNull(user.id)
        assertEquals("testLogin", user.login)
        assertTrue(user.accountList.isEmpty())
    }

    @Test
    fun testUserWithAccounts() {
        val account = Account(userId = Uuid.random(), moneyAmount = 100.0)
        val user = User(login = "testLogin", accountList = mutableListOf(account))
        assertEquals(1, user.accountList.size)
    }

    @Test
    fun testAccountCreation() {
        val userId = Uuid.random()
        val account = Account(userId = userId, moneyAmount = 500.0)
        assertNotNull(account.id)
        assertEquals(userId, account.userId)
        assertEquals(500.0, account.moneyAmount)
    }

    @Test
    fun testAccountDefaultValues() {
        val userId = Uuid.random()
        val account = Account(userId = userId, moneyAmount = 0.0)
        assertEquals(0.0, account.moneyAmount)
    }

    @Test
    fun testUserEquality() {
        val id = Uuid.random()
        val user1 = User(id = id, login = "login")
        val user2 = User(id = id, login = "login")
        assertEquals(user1, user2)
    }

    @Test
    fun testAccountEquality() {
        val id = Uuid.random()
        val userId = Uuid.random()
        val account1 = Account(id = id, userId = userId, moneyAmount = 100.0)
        val account2 = Account(id = id, userId = userId, moneyAmount = 100.0)
        assertEquals(account1, account2)
    }
}

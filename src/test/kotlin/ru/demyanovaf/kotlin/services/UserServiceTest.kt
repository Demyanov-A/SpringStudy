package ru.demyanovaf.kotlin.services

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class UserServiceTest {

    private val userService = UserService()

    @Test
    fun testCreateUser() {
        val user = userService.createUser("testLogin")
        assertNotNull(user.id)
        assertEquals("testLogin", user.login)
        assertTrue(user.accountList.isEmpty())
    }

    @Test
    fun testCreateUserSameLogin() {
        val user1 = userService.createUser("sameLogin")
        val user2 = userService.createUser("sameLogin")
        assertEquals(user1.id, user2.id)
    }

    @Test
    fun testFindById() {
        val user = userService.createUser("login1")
        val found = userService.findById(user.id)
        assertEquals(user.id, found?.id)
        assertEquals(user.login, found?.login)
    }

    @Test
    fun testFindByIdNotFound() {
        val found = userService.findById(kotlin.uuid.Uuid.parse("00000000-0000-0000-0000-000000000000"))
        assertNull(found)
    }

    @Test
    fun testShowAll() {
        userService.createUser("login1")
        userService.createUser("login2")
        userService.createUser("login3")
        val all = userService.showAll()
        assertEquals(3, all.size)
    }

    @Test
    fun testShowAllEmpty() {
        val all = userService.showAll()
        assertTrue(all.isEmpty())
    }
}

package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import ru.demyanovaf.kotlin.services.UserService
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommandsTest {

    private val userService = UserService()
    private val accountService = AccountService(100.0)

    @Test
    fun testAccountCreateCommand() {
        val command = AccountCreateCommand(userService, accountService)
        assertNotNull(command)
    }

    @Test
    fun testAccountDepositCommand() {
        val command = AccountDepositCommand(accountService)
        assertNotNull(command)
    }

    @Test
    fun testAccountTransferCommand() {
        val command = AccountTransferCommand(accountService)
        assertNotNull(command)
    }

    @Test
    fun testShowAllCommand() {
        val command = ShowAllCommand(userService)
        assertNotNull(command)
    }

    @Test
    fun testAccountWithDrawCommand() {
        val command = AccountWithDrawCommand(accountService)
        assertNotNull(command)
    }

    @Test
    fun testAccountCloseCommand() {
        val command = AccountCloseCommand(userService, accountService)
        assertNotNull(command)
    }

    @Test
    fun testFindAccountByIdCommand() {
        val command = FindAccountByIdCommand(accountService)
        assertNotNull(command)
    }

    @Test
    fun testFindUserByIdCommand() {
        val command = FindUserByIdCommand(userService)
        assertNotNull(command)
    }

    @Test
    fun testCreateUserCommand() {
        val command = CreateUserCommand(userService, accountService)
        assertNotNull(command)
    }

    @Test
    fun testIOperationCommandInterface() {
        val command = object : IOperationCommand {
            override fun execute() {}
        }
        assertNotNull(command)
    }
}

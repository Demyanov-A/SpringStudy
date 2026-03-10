package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import ru.demyanovaf.kotlin.services.UserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AccountCreateCommand(val userService: UserService, val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of user:")
        try {
            val readln = readlnOrNull().toString()
            userService.findById(Uuid.parse(readln))?.accountList?.add(accountService.createAccount(Uuid.parse(readln)))
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

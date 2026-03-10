package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import ru.demyanovaf.kotlin.services.UserService
import kotlin.uuid.ExperimentalUuidApi

class CreateUserCommand(val userService: UserService, val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter login for new user:")
        val login = readlnOrNull().toString()
        val user = userService.createUser(login).apply { accountList.add(accountService.createAccount(this.id)) }
        println("User created: User{id=${user.id}, login=${user.login}},accountList=${user.accountList}")
    }
}

package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import ru.demyanovaf.kotlin.services.UserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AccountCloseCommand(val userService: UserService, val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of account:")
        try {
            accountService.findById(Uuid.parse(readlnOrNull().toString()))?.let {
                userService.findById(it.userId)?.apply {
                    accountService.accountClose(it.id)
                    if (this.accountList.size > 1) {
                        this.accountList.remove(it)
                    }
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

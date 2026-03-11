package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AccountWithDrawCommand(val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of account and amount with delimiter ',':")
        val readln = readlnOrNull().toString().split(",")
        try {
            accountService.accountWithdraw(
                Uuid.parse(readln.first()), readln.last().toDouble()
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

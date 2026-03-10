package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AccountTransferCommand(val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of source account, id of destination account and amount with delimiter ',':")
        val readln = readlnOrNull().toString().split(",")
        try {
            accountService.accountTransfer(
                Uuid.parse(readln[0]),
                Uuid.parse(readln[1]), readln[2].toDouble()
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FindAccountByIdCommand(val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of account:")
        try{
            println("${accountService.findById(Uuid.parse(readlnOrNull().toString()))}")
        }catch (e: Exception){
            println("Error: ${e.message}")
        }
    }
}

package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.UserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FindUserByIdCommand(val userService: UserService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of user:")
        try{
            println("${userService.findById(Uuid.parse(readlnOrNull().toString()))}")
        }catch (e: Exception){
            println("Error: ${e.message}")
        }
    }
}

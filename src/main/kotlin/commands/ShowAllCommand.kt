package ru.demyanovaf.kotlin.commands

import ru.demyanovaf.kotlin.services.UserService

class ShowAllCommand(val userService: UserService) : IOperationCommand {
    override fun execute() {
        println(if (!userService.showAll().isEmpty()) userService.showAll().joinToString("\n") else "No users find!")
    }
}

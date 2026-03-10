package ru.demyanovaf.kotlin.services

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import ru.demyanovaf.kotlin.commands.AccountCloseCommand
import ru.demyanovaf.kotlin.commands.AccountCreateCommand
import ru.demyanovaf.kotlin.commands.AccountDepositCommand
import ru.demyanovaf.kotlin.commands.AccountTransferCommand
import ru.demyanovaf.kotlin.commands.AccountWithDrawCommand
import ru.demyanovaf.kotlin.commands.CreateUserCommand
import ru.demyanovaf.kotlin.commands.FindAccountByIdCommand
import ru.demyanovaf.kotlin.commands.FindUserByIdCommand
import ru.demyanovaf.kotlin.commands.IOperationCommand
import ru.demyanovaf.kotlin.commands.ShowAllCommand

@Component
class OperationsConsoleListener(userService: UserService, accountService: AccountService) {

    enum class ConsoleOperationType{
        ACCOUNT_CREATE,
        SHOW_ALL_USERS,
        ACCOUNT_CLOSE,
        ACCOUNT_WITHDRAW,
        ACCOUNT_DEPOSIT,
        ACCOUNT_TRANSFER,
        USER_CREATE,
        FIND_USER_BY_ID,
        FIND_ACCOUNT_BY_ID
    }

    val map = mapOf<ConsoleOperationType, IOperationCommand>(
        ConsoleOperationType.USER_CREATE to CreateUserCommand(userService, accountService),
        ConsoleOperationType.FIND_USER_BY_ID to FindUserByIdCommand(userService),
        ConsoleOperationType.FIND_ACCOUNT_BY_ID to FindAccountByIdCommand(accountService),
        ConsoleOperationType.ACCOUNT_CLOSE to AccountCloseCommand(userService, accountService),
        ConsoleOperationType.ACCOUNT_WITHDRAW to AccountWithDrawCommand(accountService),
        ConsoleOperationType.SHOW_ALL_USERS to ShowAllCommand(userService),
        ConsoleOperationType.ACCOUNT_DEPOSIT to AccountDepositCommand(accountService),
        ConsoleOperationType.ACCOUNT_TRANSFER to AccountTransferCommand(accountService),
        ConsoleOperationType.ACCOUNT_CREATE to AccountCreateCommand(userService, accountService),
    )

    @Bean
    fun startConsole() {
        println("Please enter one of operation type:\n" +
                "-ACCOUNT_CREATE\n" +
                "-SHOW_ALL_USERS\n" +
                "-ACCOUNT_CLOSE\n" +
                "-ACCOUNT_WITHDRAW\n" +
                "-ACCOUNT_DEPOSIT\n" +
                "-ACCOUNT_TRANSFER\n" +
                "-USER_CREATE\n" +
                "-FIND_USER_BY_ID\n" +
                "-FIND_ACCOUNT_BY_ID"
        )

        map[ConsoleOperationType.valueOf(readlnOrNull()!!)]?.execute().also { startConsole() }
    }
}
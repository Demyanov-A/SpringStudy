package ru.demyanovaf.kotlin.services

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import ru.demyanovaf.kotlin.commands.IOperationCommand
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

class AccountCreateCommand(val userService: UserService, val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute(){
        println("Enter id of user:")
        try{
            val readln = readlnOrNull().toString()
            userService.findById(Uuid.parse(readln))?.accountList?.add(accountService.createAccount(Uuid.parse(readln)))
        }catch (e: Exception){
            println("Error: ${e.message}")
        }
    }
}

class AccountDepositCommand(val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of account and amount with delimiter ',':")
        val readln = readlnOrNull().toString().split(",")
        try {
            accountService.accountDeposit(
                Uuid.parse(readln.first()), readln.last().toDouble())
        }catch (e: Exception){
            println("Error: ${e.message}")
        }
    }
}

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

class ShowAllCommand(val userService: UserService) : IOperationCommand {
    override fun execute() {
        println(if(!userService.showAll().isEmpty()) userService.showAll().joinToString("\n") else "No users find!")
    }
}

class AccountWithDrawCommand(val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter id of account and amount with delimiter ',':")
        val readln = readlnOrNull().toString().split(",")
        try {
            accountService.accountWithdraw(
                Uuid.parse(readln.first()), readln.last().toDouble())
        }catch (e: Exception){
            println("Error: ${e.message}")
        }
    }
}

class AccountCloseCommand(val userService: UserService, val accountService: AccountService): IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute()  {
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
        }catch(e:Exception){
            println("Error: ${e.message}")
        }
    }
}

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

class CreateUserCommand(val userService: UserService, val accountService: AccountService) : IOperationCommand {
    @OptIn(ExperimentalUuidApi::class)
    override fun execute() {
        println("Enter login for new user:")
        val login = readlnOrNull().toString()
        val user = userService.createUser(login).apply { accountList.add(accountService.createAccount(this.id)) }
        println("User created: User{id=${user.id}, login=${user.login}},accountList=${user.accountList}")
    }
}
package ru.demyanovaf.kotlin.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.demyanovaf.kotlin.models.Account
import ru.demyanovaf.kotlin.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Component
class OperationsConsoleListener {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var accountService: AccountService

    @OptIn(ExperimentalUuidApi::class)
    fun createUser(login: String) {
        userService.createUser(login).apply { accountList.add(accountService.createAccount(this.id)) }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun findUserById(id: Uuid): User? {
        return userService.findById(id)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun findAccountById(id: Uuid): Account? {
        return accountService.findById(id)
    }

    fun showAll() = userService.showAll()

    @OptIn(ExperimentalUuidApi::class)
    fun accountDeposit(id: Uuid) {
        accountService.accountDeposit(id, 5.0)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun addAccount(id: Uuid) {
        userService.findById(id)?.accountList?.add(accountService.createAccount(id))
    }

    @OptIn(ExperimentalUuidApi::class)
    fun accountTransfer(idSource: Uuid, idDestination: Uuid, amount: Double) {
        accountService.accountTransfer(idSource, idDestination, amount)
    }
}
package ru.demyanovaf.kotlin.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Component
class OperationsConsoleListener {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var accountService: AccountService

    @OptIn(ExperimentalUuidApi::class)
    fun createUser(login: String) =
        userService.createUser(login).apply { accountList.add(accountService.createAccount(this.id)) }

    @OptIn(ExperimentalUuidApi::class)
    fun findUserById(id: Uuid) = userService.findById(id)

    @OptIn(ExperimentalUuidApi::class)
    fun findAccountById(id: Uuid) = accountService.findById(id)

    fun showAll() = userService.showAll()

    @OptIn(ExperimentalUuidApi::class)
    fun accountDeposit(id: Uuid) = accountService.accountDeposit(id, 5.0)

    @OptIn(ExperimentalUuidApi::class)
    fun addAccount(id: Uuid) = userService.findById(id)?.accountList?.add(accountService.createAccount(id))

    @OptIn(ExperimentalUuidApi::class)
    fun accountTransfer(idSource: Uuid, idDestination: Uuid, amount: Double) =
        accountService.accountTransfer(idSource, idDestination, amount)

    @OptIn(ExperimentalUuidApi::class)
    fun accountWithdraw(id: Uuid, amount: Double) = accountService.accountWithdraw(id, amount)

    @OptIn(ExperimentalUuidApi::class)
    fun accountClose(id: Uuid) = findAccountById(id)?.let {
        findUserById(it.userId)?.apply {
            accountService.accountClose(it.id)
            if (this.accountList.size > 1) {
                this.accountList.remove(it)
            }
        }
    }
}
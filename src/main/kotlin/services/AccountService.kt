package ru.demyanovaf.kotlin.services

import org.springframework.stereotype.Service
import ru.demyanovaf.kotlin.models.Account
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class AccountService {

    val defaultAmount = 10.0

    @OptIn(ExperimentalUuidApi::class)
    private val createdAccounts = mutableListOf<Account>()

    @OptIn(ExperimentalUuidApi::class)
    fun createAccount(userId: Uuid): Account {
        return Account(userId = userId, moneyAmount = defaultAmount).apply { createdAccounts.add(this) }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun findById(id: Uuid): Account? {
        val acc = createdAccounts.find { it.id == id }
        acc ?: println("Account not found with id $id")
        return acc
    }

    @OptIn(ExperimentalUuidApi::class)
    fun accountDeposit(id: Uuid, amount: Double) {
        findById(id)?.moneyAmount += amount
    }

    @OptIn(ExperimentalUuidApi::class)
    fun accountTransfer(idSource: Uuid, idDestination: Uuid, amount: Double) {
        findById(idSource)?.let {
            if (it.moneyAmount >= amount) {
                it.moneyAmount -= amount
                findById(idDestination)?.moneyAmount += amount
            } else println("Amount of mooneyAmount less then $amount")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun accountWithdraw(id: Uuid, amount: Double) {
        findById(id)?.let {
            if (it.moneyAmount >= amount) {
                it.moneyAmount -= amount
            } else println("Amount of mooneyAmount less then $amount")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun accountClose(id: Uuid) {
        findById(id)?.let { source ->
            createdAccounts.find { destination -> destination.userId == source.userId && destination.id != source.id }?.apply {
                accountTransfer(source.id, this.id, source.moneyAmount)
                createdAccounts.remove(source)
            }?: println("Last account cant be closed")
        }
    }
}
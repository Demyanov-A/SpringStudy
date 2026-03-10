package ru.demyanovaf.kotlin.services

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AccountServiceTest {

    private val accountService = AccountService(100.0)

    @Test
    fun testCreateAccount() {
        val userId = Uuid.random()
        val account = accountService.createAccount(userId)
        assertNotNull(account.id)
        assertEquals(userId, account.userId)
        assertEquals(100.0, account.moneyAmount)
    }

    @Test
    fun testFindById() {
        val userId = Uuid.random()
        val account = accountService.createAccount(userId)
        val found = accountService.findById(account.id)
        assertEquals(account.id, found?.id)
        assertEquals(account.userId, found?.userId)
    }

    @Test
    fun testFindByIdNotFound() {
        val found = accountService.findById(Uuid.parse("00000000-0000-0000-0000-000000000000"))
        assertNull(found)
    }

    @Test
    fun testAccountDeposit() {
        val userId = Uuid.random()
        val account = accountService.createAccount(userId)
        accountService.accountDeposit(account.id, 50.0)
        assertEquals(150.0, account.moneyAmount)
    }

    @Test
    fun testAccountDepositNotFound() {
        accountService.accountDeposit(Uuid.parse("00000000-0000-0000-0000-000000000000"), 50.0)
    }

    @Test
    fun testAccountWithdraw() {
        val userId = Uuid.random()
        val account = accountService.createAccount(userId)
        accountService.accountWithdraw(account.id, 50.0)
        assertEquals(50.0, account.moneyAmount)
    }

    @Test
    fun testAccountWithdrawInsufficientFunds() {
        val userId = Uuid.random()
        val account = accountService.createAccount(userId)
        accountService.accountWithdraw(account.id, 200.0)
        assertEquals(100.0, account.moneyAmount)
    }

    @Test
    fun testAccountWithdrawNotFound() {
        accountService.accountWithdraw(Uuid.parse("00000000-0000-0000-0000-000000000000"), 50.0)
    }

    @Test
    fun testAccountTransfer() {
        val userId1 = Uuid.random()
        val userId2 = Uuid.random()
        val account1 = accountService.createAccount(userId1)
        val account2 = accountService.createAccount(userId2)
        accountService.accountTransfer(account1.id, account2.id, 50.0)
        assertEquals(50.0, account1.moneyAmount)
        assertEquals(150.0, account2.moneyAmount)
    }

    @Test
    fun testAccountTransferInsufficientFunds() {
        val userId1 = Uuid.random()
        val userId2 = Uuid.random()
        val account1 = accountService.createAccount(userId1)
        val account2 = accountService.createAccount(userId2)
        accountService.accountTransfer(account1.id, account2.id, 200.0)
        assertEquals(100.0, account1.moneyAmount)
        assertEquals(100.0, account2.moneyAmount)
    }

    @Test
    fun testAccountTransferSourceNotFound() {
        val userId2 = Uuid.random()
        val account2 = accountService.createAccount(userId2)
        accountService.accountTransfer(Uuid.parse("00000000-0000-0000-0000-000000000000"), account2.id, 50.0)
    }

    @Test
    fun testAccountTransferDestinationNotFound() {
        val userId1 = Uuid.random()
        val account1 = accountService.createAccount(userId1)
        accountService.accountTransfer(account1.id, Uuid.parse("00000000-0000-0000-0000-000000000000"), 50.0)
        assertEquals(50.0, account1.moneyAmount)
    }

    @Test
    fun testAccountCloseNotLastAccount() {
        val userId = Uuid.random()
        val account1 = accountService.createAccount(userId)
        val account2 = accountService.createAccount(userId)
        accountService.accountClose(account1.id)
    }

    @Test
    fun testAccountCloseLastAccount() {
        val userId = Uuid.random()
        val account = accountService.createAccount(userId)
        accountService.accountClose(account.id)
    }

    @Test
    fun testAccountCloseNotFound() {
        accountService.accountClose(Uuid.parse("00000000-0000-0000-0000-000000000000"))
    }
}

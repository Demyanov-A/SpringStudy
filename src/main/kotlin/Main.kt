package ru.demyanovaf.kotlin

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.demyanovaf.kotlin.services.OperationsConsoleListener
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext("ru.demyanovaf.kotlin")

    val operationsConsoleListener = context.getBean(OperationsConsoleListener::class.java)
    operationsConsoleListener.createUser("login1")
    val users = operationsConsoleListener.showAll()
    operationsConsoleListener.accountClose(users.first().accountList.first().id)
    println(users)
    operationsConsoleListener.addAccount(users.first().id)
    println(users)
    /*operationsConsoleListener.accountDeposit(users.first().accountList.first().id)
    println(users)
    operationsConsoleListener.accountDeposit(users.first().accountList.first().id)
    println(users)*/
/*    operationsConsoleListener.accountTransfer(
        users.first().accountList.first().id,
        users.first().accountList.last().id,
        11.0
    )
    println(users)*/
    operationsConsoleListener.accountClose(users.first().accountList.first().id)
    println(users)
}


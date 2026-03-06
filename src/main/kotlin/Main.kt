package ru.demyanovaf.kotlin

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.demyanovaf.kotlin.services.UserService

fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext()

    val userService = context.getBean(UserService::class.java)
    userService.toString()
}


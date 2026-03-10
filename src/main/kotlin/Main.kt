package ru.demyanovaf.kotlin

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun main(args: Array<String>) {
    AnnotationConfigApplicationContext("ru.demyanovaf.kotlin")
}
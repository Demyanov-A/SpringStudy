package ru.demyanovaf.kotlin.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class User @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.random(),
    val login: String,
    val accountList: List<Account> = emptyList(),
)
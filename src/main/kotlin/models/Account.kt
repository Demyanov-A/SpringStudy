package ru.demyanovaf.kotlin.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Account @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.random(),
    val userId: Uuid,
    val mooneyAmount: Double,
)
package com.pingpad.coredomain.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Mapper<I, O> {
    fun map(from: I): O
    fun mapList(from: List<I>): List<O> = from.map { map(it) }
    fun mapFlow(from: Flow<List<I>>): Flow<List<O>> = from.map { mapList(it) }
    fun mapSingleFlow(from: Flow<I>): Flow<O> = from.map { map(it) }
    fun <C : Collection<I>> mapCollection(from: C): List<O> = from.map { map(it) }
    fun mapNestedList(from: List<List<I>>): List<List<O>> = from.map { mapList(it) }
}
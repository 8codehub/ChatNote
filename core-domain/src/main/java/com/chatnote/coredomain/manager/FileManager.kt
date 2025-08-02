package com.chatnote.coredomain.manager

interface FileManager {

    suspend fun deleteFileIfExist(path: String): Result<Boolean>
}
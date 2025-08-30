package com.chatnote.coredomain.manager

import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

internal class FileManagerImpl @Inject constructor() : FileManager {
    override suspend fun deleteFileIfExist(path: String): Result<Boolean> {
        return try {
            val actualPath = if (path.startsWith("file://")) {
                path.removePrefix("file://")
            } else path

            val file = File(actualPath)
            if (file.exists()) {
                Result.success(file.delete())
            }
            Result.failure(ResultError.DeleteFileFail(throwable = FileNotFoundException()))
        } catch (e: Exception) {
            Result.failure(ResultError.DeleteFileFail(throwable = e))
        }
    }
}
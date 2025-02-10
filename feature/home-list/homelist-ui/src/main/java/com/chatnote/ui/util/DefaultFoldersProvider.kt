package com.chatnote.ui.util

import android.content.Context
import androidx.annotation.StringRes
import chatnote.homelistui.R
import com.chatnote.domain.model.DefaultFolder

object DefaultFoldersProvider {

    fun loadFolders(context: Context): List<DefaultFolder> {

        val specificFolders = listOf(
            FolderAsset("work.webp", R.string.default_folder_work),
            FolderAsset("plane.webp", R.string.default_folder_travel),
            FolderAsset("car.webp", R.string.default_folder_car),
            FolderAsset("music.webp", R.string.default_folder_music)
        )

        val folderList = mutableListOf<DefaultFolder>()

        specificFolders.forEach { folderAsset ->
            try {
                folderList.add(
                    DefaultFolder(
                        name = context.getString(folderAsset.nameRes),
                        iconUri = "file:///android_asset/icons/${folderAsset.assetName}"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return folderList
    }
}

private data class FolderAsset(
    val assetName: String,
    @StringRes val nameRes: Int
)


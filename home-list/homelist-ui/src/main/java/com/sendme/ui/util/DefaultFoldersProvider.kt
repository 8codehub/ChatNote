package com.sendme.ui.util

import android.content.Context
import androidx.annotation.StringRes
import com.sendme.domain.model.DefaultFolder
import com.sendme.homelistui.R

object DefaultFoldersProvider {

    fun loadFolders(context: Context): List<DefaultFolder> {

        val specificFolders = listOf(
            FolderAsset("work.png", R.string.default_folder_work),
            FolderAsset("heart.png", R.string.default_folder_love),
            FolderAsset("car.png", R.string.default_folder_car),
            FolderAsset("music.png", R.string.default_folder_music)
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


package com.chatnote.ui.folderlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.homelistui.R
import coil.compose.AsyncImage
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.ui.model.UiFolder
import com.chatnote.ui.model.UiNoteExtra
import com.chatnote.content.R as CR


@Composable
fun FolderCard(
    folder: UiFolder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp)
    ) {

        CircularImage(
            modifier = Modifier.padding(vertical = 4.dp),
            imageUri = folder.iconUri.orEmpty(),
            iconSize = 56.dp,
            iconPadding = 0.dp,
            borderColor = MaterialTheme.colorScheme.onBackground,
            borderWidth = 1.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            StyledText(
                text = folder.name,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (folder.lastNoteExtras.isEmpty()) {
                    StyledText(
                        modifier = Modifier.weight(1f, fill = false),
                        text = folder.lastNote.ifEmpty {
                            stringResource(CR.string.empty_folder_hint)
                        },
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = if (folder.lastNote.isEmpty()) {
                            FontStyle.Italic
                        } else {
                            FontStyle.Normal
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .height(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        folder.lastNoteExtras
                            .filterIsInstance<UiNoteExtra.Image>() // only show previews for images
                            .take(3)
                            .forEach { imageExtra ->
                                AsyncImage(
                                    model = imageExtra.value,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .height(16.dp)
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                            }

                        if (folder.lastNoteExtras.size > 3) {
                            StyledText(
                                text = "+${folder.lastNoteExtras.size - 3}",
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic,

                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                if (!folder.lastNoteCreatedDate.isNullOrEmpty()) {
                    StyledText(
                        text = folder.lastNoteCreatedDate,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        maxLines = 1,
                        fontStyle = FontStyle.Italic,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        if (folder.isPinned) {
            CircularImage(
                contentDescription = stringResource(CR.string.pinned_folder),
                modifier = Modifier.rotate(45f),
                iconSize = 18.dp,
                drawableRes = R.drawable.ic_pin,
                iconPadding = 6.dp,
                contentScale = ContentScale.Fit,
                imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                backgroundColor = Color.Transparent
            )
        }

    }

}
package com.chatnote.directnotesdomain.usecase


import com.chatnote.directnotesdomain.model.NoteActionType
import com.chatnote.directnotesdomain.model.NoteActionableContent
import com.chatnote.directnotesdomain.model.NoteActionableItem
import javax.inject.Inject

internal class ExtractActionableContentUseCaseImpl @Inject constructor() : ExtractActionableContentUseCase {

    override fun invoke(fullMessage: String): NoteActionableContent {
        val noteActionableItems = mutableListOf<NoteActionableItem>()
        extractPhoneNumbers(fullMessage).forEach { phone ->
            noteActionableItems.add(
                NoteActionableItem(
                    phone,
                    listOf(
                        NoteActionType.Copy(content = phone),
                        NoteActionType.SMS(phoneNumber = phone),
                        NoteActionType.Call(phoneNumber = phone)
                    )
                )
            )
        }

        extractEmails(fullMessage).forEach { email ->
            noteActionableItems.add(
                NoteActionableItem(
                    email,
                    listOf(
                        NoteActionType.Copy(content = email),
                        NoteActionType.OpenEmail(email = email)
                    )
                )
            )
        }

        extractUrls(fullMessage).forEach { url ->
            noteActionableItems.add(
                NoteActionableItem(
                    url,
                    listOf(NoteActionType.Copy(content = url), NoteActionType.OpenWeb(url = url))
                )
            )
        }

        return NoteActionableContent(
            fullContent = fullMessage,
            noteActionableItems = noteActionableItems
        )
    }

    private fun extractPhoneNumbers(text: String): List<String> {
        val regex = Regex(
            """\+?\(?\d{1,4}\)?[-.\s]?\d{2,4}[-.\s]?\d{2,4}[-.\s]?\d{2,6}"""
        )
        val rawPhones = regex.findAll(text).map { it.value.trim() }.distinct().toList()
        return rawPhones.map { normalizePhoneNumber(it) }
    }

    private fun normalizePhoneNumber(phone: String): String {
        return phone.replace(Regex("[\\s\\-\\.\\(\\)]"), "")
    }

    private fun extractEmails(text: String): List<String> {
        val emails = mutableListOf<String>()
        val matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(text)
        while (matcher.find()) {
            emails.add(matcher.group() ?: "")
        }
        return emails.distinct()
    }

    private fun extractUrls(text: String): List<String> {
        val urlRegex = Regex("""(https?:\/\/|www\.)[^\s]+""")
        val matches = urlRegex.findAll(text).map {
            it.value.trim().trimEnd('.', ',', '!', '?') // Remove trailing punctuation
        }.distinct().toList()
        return matches
    }
}

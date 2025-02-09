package com.chatnote.directnotesdomain.usecase


import com.chatnote.directnotesdomain.model.ActionType
import com.chatnote.directnotesdomain.model.ActionableItem
import com.chatnote.directnotesdomain.model.NoteActionableContent
import javax.inject.Inject

class ExtractActionableContentUseCaseImpl @Inject constructor() : ExtractActionableContentUseCase {
    override fun invoke(fullMessage: String): NoteActionableContent {
        val actionableItems = mutableListOf<ActionableItem>()

        extractPhoneNumbers(fullMessage).forEach { phone ->
            actionableItems.add(
                ActionableItem(
                    phone,
                    listOf(
                        ActionType.Copy(content = phone),
                        ActionType.SMS(phoneNumber = phone),
                        ActionType.Call(phoneNumber = phone)
                    )
                )
            )
        }

        extractEmails(fullMessage).forEach { email ->
            actionableItems.add(
                ActionableItem(
                    email,
                    listOf(ActionType.Copy(content = email), ActionType.OpenEmail(email = email))
                )
            )
        }

        extractUrls(fullMessage).forEach { url ->
            actionableItems.add(
                ActionableItem(
                    url,
                    listOf(ActionType.Copy(content = url), ActionType.OpenWeb(url = url))
                )
            )
        }

        return NoteActionableContent(
            fullContent = fullMessage,
            actionableItems = actionableItems
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

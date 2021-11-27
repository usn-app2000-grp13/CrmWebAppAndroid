package no.usn.gruppe4.crmwebappandroid.models.mail

data class MailRequest(
    var mailText: String,
    var mailHtml: String,
    var recipient: String,
    var mailSubject: String,
    var mailFrom: String,
    var mailReturn: String
) {
}
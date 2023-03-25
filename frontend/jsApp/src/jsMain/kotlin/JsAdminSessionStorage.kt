import net.matsudamper.social.frontend.common.viewmodel.admin.AdminSession
import net.matsudamper.social.frontend.common.viewmodel.admin.AdminSessionStorage
import net.matsudamper.social.shared.Cookies

internal class JsAdminSessionStorage : AdminSessionStorage {
    override fun readSession(): AdminSession? {
        val adminSessionValue = CookieStorage[Cookies.AdminSession.key]
        return if (adminSessionValue != null) {
            AdminSession(
                value = adminSessionValue,
            )
        } else {
            null
        }
    }
}
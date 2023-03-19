package navigation

enum class Screen : Direction {
    Root {
        override val title: String = ""
        override val url: String = "/"
    },
    Login {
        override val title: String = "ログイン"
        override val url: String = "/login"
    }
}
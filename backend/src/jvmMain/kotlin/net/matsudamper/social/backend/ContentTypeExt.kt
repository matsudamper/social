package net.matsudamper.social.backend

import io.ktor.http.ContentType

val ContentType.Application.ActivityJson: ContentType
    get() = ContentType.parse("application/activity+json")
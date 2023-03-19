package net.matsudamper.social

import io.ktor.http.ContentType

val ContentType.Application.ActivityJson: ContentType
    get() = ContentType.parse("application/activity+json")
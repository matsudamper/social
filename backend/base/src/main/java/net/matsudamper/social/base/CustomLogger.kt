package net.matsudamper.social.base

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import org.slf4j.Logger

object CustomLogger {
    object ParseFail {
        private val logger = LoggerFactory.getLogger("ParseFail")!!

        fun log(clazz: KClass<*>, info: String) {
            logger.info("[$clazz]: $info")
        }
    }
    object General {
        private val logger = LoggerFactory.getLogger("ParseFail")!!

        fun info(message: String) {
            logger.info(message)
        }
    }
}

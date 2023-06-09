package chistousov.ilya.common

/**
 * Common global variables
 * Needed to call [Core.init] before any interaction with core
 */
object Core {

    private lateinit var coreProvider: CoreProvider

    val logger: Logger get() = coreProvider.logger

    val resource: Resource get() = coreProvider.resource

    fun init(coreProvider: CoreProvider) {
        this.coreProvider = coreProvider
    }
}